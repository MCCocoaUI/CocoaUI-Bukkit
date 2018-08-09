package net.mcbbs.cocoaui.managers.picturemanager;

import java.io.File;
import java.io.FileFilter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.mcbbs.cocoaui.CocoaUI;
import net.mcbbs.cocoaui.pluginmessage.packages.OutPictureUpdateSent;
import net.mcbbs.cocoaui.utils.config.ConfigException;

public class PicturesManager {
	public Map<String, PluginPictureManager> picturemanagers = Maps.newHashMap();
	public Map<UUID, PictureEditor> pictureeditors = Maps.newHashMap();
	public Map<PictureName, Future<PictureInfo>> updatelist = Maps.newHashMap();
	private ExecutorService pool = Executors.newFixedThreadPool(4);
	private Set<PictureName> finish = Sets.newHashSet();
	private int loaded;
	private int timer = 0;
	boolean firstLoad = true;
	BukkitTask task;

	public void init() {
		this.loadManagers();
		CocoaUI.getLog().info("[PictureManager]所有信息加载完成，等待异步验证 ");

		this.startTask();
	}

	public boolean setURL(Player p, String url) {
		if (this.pictureeditors.containsKey(p.getUniqueId())) {
			PictureEditor editor = this.pictureeditors.get(p.getUniqueId());
			if (picturemanagers.containsKey(editor.getPluginName())) {
				return this.picturemanagers.get(editor.getPluginName()).setURL(editor.getName(), url);
			}
		}
		return false;
	}

	private void startTask() {
		Bukkit.getScheduler().runTaskTimer(CocoaUI.getPlugin(CocoaUI.class), new Runnable() {
			@Override
			public void run() {
				onTick();
			}
		}, 1L, 1L);
	}

	public boolean editPicture(Player p, String pluginName, String name) {
		PluginPictureManager pl = this.picturemanagers.get(pluginName);
		if (pl != null) {
			if (pl.contains(name)) {
				this.pictureeditors.put(p.getUniqueId(), new PictureEditor(p.getUniqueId(), pluginName, name));
				return true;
			}
		}
		return false;
	}

	public boolean registerPlugin(String pluginName) {
		if (this.picturemanagers.containsKey(pluginName)) {
			return false;
		}
		try {
			this.picturemanagers.put(pluginName, new PluginPictureManager(pluginName));
			return true;
		} catch (ConfigException e) {
			e.printStackTrace();
		}
		return false;
	}

	public PluginPictureManager getPluginPictureManager(String name) {
		return this.picturemanagers.get(name);
	}

	public void loadManagers() {
		File f = new File(CocoaUI.getKDataFolder() + "/picconfig/");
		for (File file : f.listFiles(new FileFilter() {
			@Override
			public boolean accept(File arg0) {
				return arg0.toString().endsWith("yml");
			}
		})) {
			this.loadFromFile(file);
		}
	}

	public void loadFromFile(File f) {
		String name = f.getName();
		this.loadManager(name.substring(0, name.length() - 4));

	}

	public void loadManager(String name) {
		try {
			PluginPictureManager manager = new PluginPictureManager(name);
			this.picturemanagers.put(name, manager);
		} catch (ConfigException e) {
			e.printStackTrace();
		}
	}

	public void sendUpdatePackage(Player p) {

		for (PluginPictureManager manager : picturemanagers.values()) {
			CocoaUI.getPluginMessageManager().sendPackage(manager.getPackage(), p);
		}
		CocoaUI.getPluginMessageManager().sendPackage(new OutPictureUpdateSent(), p);

	}

	public void save() {
		for (PluginPictureManager manager : this.picturemanagers.values()) {
			manager.save();
		}
	}

	public void reloadPictureInfo(String url, String name, String pluginName) {
		this.updatelist.put(new PictureName(name, pluginName),
				this.pool.submit(new PictureInfoLoader(url, name, pluginName)));
		return;
	}

	public void onTick() {
		timer++;
		if (this.updatelist.isEmpty()) {
			if (this.firstLoad) {
				this.firstLoad = false;
				CocoaUI.getLog().info("[CocoaUI]插件所有图片信息已经加载完成");
				return;
			}
		}
		if (timer == 30) {
			if(!this.isFinish()) {
			CocoaUI.getLog().info("已经加载(" + this.loaded + "/" + (this.loaded + this.updatelist.size() + ")"));
			this.timer = 0;}
		}
		for (Entry<PictureName, Future<PictureInfo>> entry : this.updatelist.entrySet()) {
			if (entry.getValue().isDone()) {
				try {
					this.updateInfo(entry.getKey(), entry.getValue().get());
					this.finish.add(entry.getKey());
					this.loaded++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		}

		for (PictureName pl : this.finish) {
			this.updatelist.remove(pl);
		}
	}

	private void updateInfo(PictureName key, PictureInfo value) {
		if (this.picturemanagers.containsKey(key.pluginName)) {
			this.picturemanagers.get(key.pluginName).updateInfo(key.name, value);
		}
	}

	public boolean isFinish() {
		return !this.firstLoad;
	}
	public void onDisable() {
		this.task.cancel();
	}

}

class PictureName {
	String name;
	String pluginName;

	public PictureName(String name, String pluginName) {
		this.name = name;
		this.pluginName = pluginName;
	}
}
