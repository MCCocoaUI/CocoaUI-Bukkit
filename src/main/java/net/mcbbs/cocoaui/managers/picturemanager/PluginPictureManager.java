package net.mcbbs.cocoaui.managers.picturemanager;

import java.io.IOException;
import java.util.Map;

import com.google.common.collect.Maps;
import com.google.gson.Gson;

import net.mcbbs.cocoaui.CocoaUI;
import net.mcbbs.cocoaui.pluginmessage.packages.OutPictureUpdate;
import net.mcbbs.cocoaui.pluginmessage.packages.OutSinglePictureUpdate;
import net.mcbbs.cocoaui.utils.config.AbstractConfiguration;
import net.mcbbs.cocoaui.utils.config.ConfigException;

public final class PluginPictureManager extends AbstractConfiguration {
	private Map<String, Picture> pics = Maps.newHashMap();
	private Map<String, PictureChenge> pcs = Maps.newHashMap();
	private String pluginName;

	public PluginPictureManager(String pluginName) throws ConfigException {
		super("picconfig/" + pluginName + ".yml", false, "pluginName.yml created", "cannot create pictures.yml");
		this.pluginName = pluginName;
		this.loadConfig();
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
			this.pcs.put(name, new PictureChenge(PictureChenge.SETURL, name, null));
			return true;
		}
		return false;
	}

	public boolean removePicture(String name) {
		if (this.pics.containsKey(name)) {
			this.pics.remove(name);
			this.pcs.put(name, new PictureChenge(PictureChenge.REMOVE, name, null));
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

	private Map<String, String> getPicData(Picture p) {
		Map<String, String> map = Maps.newHashMap();
		map.put("name", p.getName());
		map.put("url", p.getUrl());
		map.put("md5", p.getMD5());
		return map;
	}

	public OutPictureUpdate getPackage() {
		Map<String, Map<String, String>> content = Maps.newHashMap();
		for (Picture pic : this.pics.values()) {
			content.put(pic.getName(), this.getPicData(pic));
		}
		Map<String, Object> back = Maps.newHashMap();
		back.put("pluginName", this.getpluginName());
		back.put("picAmount", this.pics.size());
		back.put("content", content);
		return new OutPictureUpdate(new Gson().toJson(back));
	}

	public void save() {
		for (Picture pic : this.pics.values()) {
			super.getConfig().set(pic.getName(), pic);
		}
		try {
			super.saveConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public OutSinglePictureUpdate getUpdatePackage(String name, Picture p) {
		PictureChenge chenge = this.pcs.get(name);
		return new OutSinglePictureUpdate(this.pluginName, p, chenge.state);
	}

	public boolean update(String name) {
		if (this.pcs.containsKey(name)) {
			if(this.pics.containsKey(name)) {
				Picture pic = this.pics.get(name);
				OutSinglePictureUpdate pack = this.getUpdatePackage(name, pic);
				CocoaUI.getPluginMessageManager().sendAll(pack);
			}
		}
		return false;
	}

}

class PictureChenge {
	public final static int REMOVE = 3;
	public final static int SETURL = 1;

	PictureChenge(int state, String now, String old) {
		this.state = state;
		this.nowName = now;
		this.oldName = old;
	}

	int state;
	String nowName;
	String oldName;
}
