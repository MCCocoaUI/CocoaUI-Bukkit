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
	/**
	 * 插件图片管理器
	 * @author ChenJi
	 *
	 */
public final class PluginPicturesManager extends AbstractConfiguration {
	private Map<String, Picture> pictures = Maps.newHashMap();
	private Map<String, PictureChange> picturechanges = Maps.newHashMap();

	private String pluginName;

	public PluginPicturesManager(String pluginName) throws ConfigException {
		super("picconfig/" + pluginName + ".yml", false, "pluginName.yml created", "cannot create pictures.yml");
		this.pluginName = pluginName;
		CocoaUI.getLog().info("[PictureManager]正在加載 " + pluginName);

		this.loadConfig();
	}

	/**
	 * 加載config
	 */
	@Override
	public void loadConfig() {
		for (String name : super.getConfig().getKeys(false)) {
			Picture picture = (Picture) super.getConfig().get(name);
			this.pictures.put(picture.getName(), picture);
			this.check(picture);
		}
	}

	private void check(Picture picture) {
		if (picture.check()) {
			picture.setLock(true);
			CocoaUI.getPicturesManager().reloadPictureInfo(picture.getUrl(), picture.getName(), pluginName);
		}

	}

	/**
	 * 重新设置URL
	 * 
	 * @param url  url
	 * @param name name
	 * @return 是否成功，图片未找到则返回false
	 */
	public boolean setURL(String name, String url) {
		if (this.pictures.containsKey(name)) {
			this.pictures.get(name).setUrl(url);
			CocoaUI.getPicturesManager().reloadPictureInfo(url, name, pluginName);
			this.picturechanges.put(name, new PictureChange(PictureChange.SETURL, name, null));
			return true;
		}
		return false;
	}

	/**
	 * 删除图片
	 * 
	 * @param name 图片名称
	 * @return 是否成功，图片未找到则返回false
	 */
	public boolean removePicture(String name) {
		if (this.pictures.containsKey(name)) {
			this.pictures.remove(name);
			this.picturechanges.put(name, new PictureChange(PictureChange.REMOVE, name, null));
			this.update(name);
			return true;
		}
		return false;
	}

	/**
	 * 通过名称获取图片 如果未找到则返回null
	 * 
	 * @param name 图片名称
	 * @return 图片
	 */
	public Picture getPicture(String name) {
		return this.pictures.get(name);
	}

	/**
	 * 获取这个管理器所属的插件名称
	 * 
	 * @return 插件名称
	 */
	public String getpluginName() {
		return this.pluginName;
	}

	/**
	 * 直接加载图片并立即重载图片信息，注意如果出现同名会直接覆盖。
	 * 
	 * @param picture 图片
	 */
	public void loadPicture(Picture picture) {
		this.pictures.put(picture.getName(), picture);
		CocoaUI.getPicturesManager().reloadPictureInfo(picture.getUrl(), picture.getName(), pluginName);
	}

	/**
	 * 获取这个管理器内是否包含某个图片
	 * 
	 * @param name 图片名称
	 * @return 是否存在
	 */
	public boolean contains(String name) {
		return this.pictures.containsKey(name);
	}

	private Map<String, String> getPictureData(Picture p) {
		Map<String, String> map = Maps.newHashMap();
		map.put("name", p.getName());
		map.put("url", p.getUrl());
		map.put("md5", p.getMD5());
		return map;
	}

	/**
	 * 获取这个管理器的更新包
	 * 
	 * @return OutPictureUpdate
	 */
	public OutPictureUpdate getPackage() {
		Map<String, Map<String, String>> content = Maps.newHashMap();
		for (Picture pic : this.pictures.values()) {
			content.put(pic.getName(), this.getPictureData(pic));
		}
		Map<String, Object> back = Maps.newHashMap();
		back.put("pluginName", this.getpluginName());
		back.put("picAmount", this.pictures.size());
		back.put("content", content);
		return new OutPictureUpdate(new Gson().toJson(back));
	}

	/**
	 * 保存
	 */
	public void save() {
		for (Picture pic : this.pictures.values()) {
			super.getConfig().set(pic.getName(), pic);
		}
		try {
			super.saveConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取修改过的图片的更新包
	 * @param name 名称
	 * @param p 图片
	 * @return OutSinglePictureUpdate
	 */
	public OutSinglePictureUpdate getUpdatePackage(String name, Picture p) {
		PictureChange chenge = this.picturechanges.get(name);
		return new OutSinglePictureUpdate(this.pluginName, p, chenge.state);
	}
	/**
	 * 发送更新包
	 * @param 图片信息
	 */
	public void update(String name) {
		if (this.picturechanges.containsKey(name)) {
			if (this.pictures.containsKey(name)) {
				Picture pic = this.pictures.get(name);
				OutSinglePictureUpdate pack = this.getUpdatePackage(name, pic);
				CocoaUI.getPluginMessageManager().sendAll(pack);
			}
		}
	}
	void updateInfo(String name, PictureInfo value) {
		if (this.pictures.containsKey(name)) {
			if (this.picturechanges.containsKey(name)) {
				this.update(name);
			}
			this.pictures.get(name).setInfo(value);
		}

	}

}

class PictureChange {
	public final static int REMOVE = 3;
	public final static int SETURL = 1;

	PictureChange(int state, String now, String old) {
		this.state = state;
		this.nowName = now;
		this.oldName = old;
	}

	int state;
	String nowName;
	String oldName;
}
