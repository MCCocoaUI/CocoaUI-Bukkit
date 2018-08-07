package net.mcbbs.cocoaui.managers.picturemanager;

import java.io.File;
import java.io.FileFilter;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.google.common.collect.Maps;
import net.mcbbs.cocoaui.CocoaUI;
import net.mcbbs.cocoaui.utils.config.ConfigException;

public class PicturesManager {
	public Map<String, PluginPictureManager> picturemanagers = Maps.newHashMap();
	public Map<UUID, PictureEditor> pictureeditors = Maps.newHashMap();

	public PicturesManager() {
		loadManagers();
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

	public void save() {
		for (PluginPictureManager manager : this.picturemanagers.values()) {
			manager.save();
		}
	}
	
}
