package net.mcbbs.cocoaui.managers.picturemanager;

import java.util.Map;

import com.google.common.collect.Maps;
import com.google.gson.Gson;

import net.mcbbs.cocoaui.pluginmessage.packages.OutPictureUpdate;
import net.mcbbs.cocoaui.utils.config.AbstractConfiguration;
import net.mcbbs.cocoaui.utils.config.ConfigException;

public final class PluginPictureManager extends AbstractConfiguration {
	private Map<String, Picture> pics = Maps.newHashMap();
	private String pluginName;

	public PluginPictureManager(String pluginName) throws ConfigException {
		super("picconfig/" + pluginName + ".yml", false, "pictures.yml created", "cannot create pictures.yml");
		this.pluginName = pluginName;
	}

	@Override
	public void loadConfig() {
		for (String name : super.getConfig().getKeys(false)) {
			this.loadPicture((Picture) super.getConfig().get(name));
		}
	}

	public boolean setURL(String name, String url) {
		if (this.pics.containsKey(name)) {
			this.pics.get(name).setUrl(url);
			return true;
		}
		return false;
	}

	public Picture getPicture(String name) {
		return this.pics.get(name);
	}

	public String getpluginName() {
		return this.pluginName;
	}

	public void loadPicture(Picture p) {
		this.pics.put(p.getName(), p);
	}

	public boolean contains(String name) {
		return this.pics.containsKey(name);
	}

	public OutPictureUpdate getPackage() {
		Map<String, String> content = Maps.newHashMap();
		for (Picture pic : this.pics.values()) {
			content.put(pic.getName(), pic.getUrl());
		}
		Map<String, Object> back = Maps.newHashMap();
		back.put("pluginName", this.getpluginName());
		back.put("picAmount", this.pics.size());
		back.put("content", content);
		return new OutPictureUpdate(new Gson().toJson(back));
	}

}
