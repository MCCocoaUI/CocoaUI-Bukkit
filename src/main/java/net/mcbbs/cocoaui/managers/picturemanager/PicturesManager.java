package net.mcbbs.cocoaui.managers.picturemanager;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
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
import net.mcbbs.cocoaui.pluginmessage.packages.OutOpenPictureChooser;
import net.mcbbs.cocoaui.pluginmessage.packages.OutPictureUpdateSent;
import net.mcbbs.cocoaui.utils.config.ConfigException;

/**
 * 图片管理器主类
 *
 * @author ChenJi
 *
 */
public class PicturesManager {

	private Map<String, PluginPicturesManager> pictureManagers = Maps.newHashMap();
	private Map<UUID, PictureEditor> pictureEditors = Maps.newHashMap();
	private Map<PictureName, Future<PictureInfo>> updateList = Maps.newHashMap();
	private ExecutorService pool = Executors.newFixedThreadPool(4);
	private Set<PictureName> finish = Sets.newHashSet();
	private HashMap<PictureName, PicturesInfoLoader> waitLoadList = Maps.newHashMap();
	private int loaded;
	private int timer = 0;
	private boolean firstLoad = true;
	private BukkitTask task;

	/**
	 * init 不解釋
	 */
	public void init() {
		this.loadManagers();
		CocoaUI.getLog().info("[PictureManager]所有信息加载完成，等待异步验证 ");
	}

	/**
	 * 重新设置URL
	 *
	 * @param p   玩家
	 * @param url url
	 * @return 是否成功，图片未找到则返回false
	 */
	public boolean setURL(Player p, String url) {
		if (this.pictureEditors.containsKey(p.getUniqueId())) {
			PictureEditor editor = this.pictureEditors.get(p.getUniqueId());
			if (pictureManagers.containsKey(editor.getPluginName())) {
				return this.pictureManagers.get(editor.getPluginName()).setURL(editor.getName(), url);
			}
		}
		return false;
	}

	private void startTask() {
		this.task = Bukkit.getScheduler().runTaskTimer(CocoaUI.getPlugin(CocoaUI.class), new Runnable() {
			@Override
			public void run() {
				onTick();
			}
		}, 1L, 1L);
	}

	/**
	 * 編輯圖片
	 *
	 * @param p          玩家
	 * @param pluginName 插件名称
	 * @param name       图片名称
	 * @return 是否成功记录，如果图片未找到则返回false
	 */
	public boolean editPicture(Player p, String pluginName, String name) {
		PluginPicturesManager pl = this.pictureManagers.get(pluginName);
		if (pl != null) {
			if (pl.contains(name)) {
				this.pictureEditors.put(p.getUniqueId(), new PictureEditor(p.getUniqueId(), pluginName, name));
				return true;
			}
		}
		return false;
	}

	/**
	 * 注册插件，申请PluginPicturesManager
	 *
	 * @param pluginName 插件名称
	 * @return 如果插件已经存在则返回false
	 */
	public boolean registerPlugin(String pluginName) {
		if (this.pictureManagers.containsKey(pluginName)) {
			return false;
		}
		try {
			this.pictureManagers.put(pluginName, new PluginPicturesManager(pluginName));
			return true;
		} catch (ConfigException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 根据插件名称获取插件图片管理器
	 *
	 * @param name 插件名
	 * @return 插件图片管理器
	 */
	public PluginPicturesManager getPluginPictureManager(String name) {
		return this.pictureManagers.get(name);
	}

	private void loadManagers() {
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

	private void loadFromFile(File f) {
		String name = f.getName();
		this.loadManager(name.substring(0, name.length() - 4));

	}

	private void loadManager(String name) {
		try {
			PluginPicturesManager manager = new PluginPicturesManager(name);
			this.pictureManagers.put(name, manager);
		} catch (ConfigException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 为某个玩家发送图片更新包
	 *
	 * @param p 玩家
	 */
	public void sendUpdatePackage(Player p) {

		for (PluginPicturesManager manager : pictureManagers.values()) {
			CocoaUI.getPluginMessageManager().sendPackage(manager.getPackage(), p);
		}
		CocoaUI.getPluginMessageManager().sendPackage(new OutPictureUpdateSent(), p);

	}

	/**
	 * 保存
	 */
	public void save() {
		for (PluginPicturesManager manager : this.pictureManagers.values()) {
			manager.save();
		}
	}

	/**
	 * 请求根据URL重载图片的MD5，wdith，height。
	 *
	 * @param url        URL地址
	 * @param name       图片名称
	 * @param pluginName 所属插件名称
	 */
	public void reloadPictureInfo(String url, String name, String pluginName) {
		if (!this.firstLoad) {
			this.updateList.put(new PictureName(name, pluginName),
					this.pool.submit(new PicturesInfoLoader(url, name, pluginName)));
			return;
		}
		this.waitLoadList.put(new PictureName(name, pluginName), new PicturesInfoLoader(url, name, pluginName));
		return;
	}

	public void onServerDone() {
		for (Entry<PictureName, PicturesInfoLoader> entry : this.waitLoadList.entrySet()) {
			this.updateList.put(entry.getKey(), this.pool.submit(entry.getValue()));
		}
		this.startTask();
	}

	void onTick() {
		timer++;
		if (this.updateList.isEmpty()) {
			if (this.firstLoad) {
				this.firstLoad = false;
				CocoaUI.getLog().info("[CocoaUI]插件所有图片信息已经加载完成");
				return;
			}
		}
		if (timer == 30) {
			if (!this.isFinish()) {
				CocoaUI.getLog().info("已经加载(" + this.loaded + "/" + (this.loaded + this.updateList.size() + ")"));
				this.timer = 0;
			}
		}
		for (Entry<PictureName, Future<PictureInfo>> entry : this.updateList.entrySet()) {
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
			this.updateList.remove(pl);
		}
	}

	private void updateInfo(PictureName key, PictureInfo value) {
		if (this.pictureManagers.containsKey(key.pluginName)) {
			this.pictureManagers.get(key.pluginName).updateInfo(key.name, value);
		}
	}

	/**
	 * 查看图片管理器是否初始化完毕。 （遍历图片加载信息是异步操作，所以可能出现服务端启动完成却加载不完的现象，具体以这个函数的返回值为准）
	 *
	 * @return 是否加载完成
	 */
	public boolean isFinish() {
		return !this.firstLoad;
	}

	public void onDisable() {
		if (this.task != null)
			this.task.cancel();
	}

	public void sendUpdateRequest(Player p, String pluginName, String name) {
		this.pictureEditors.put(p.getUniqueId(), new PictureEditor(p.getUniqueId(), pluginName, name));
		CocoaUI.getPluginMessageManager().sendPackage(new OutOpenPictureChooser(), p);
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
