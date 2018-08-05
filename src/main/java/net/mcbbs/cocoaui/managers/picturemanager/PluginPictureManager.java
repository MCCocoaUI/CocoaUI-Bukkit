package net.mcbbs.cocoaui.managers.picturemanager;

import java.util.Map;

import com.google.common.collect.Maps;

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

	}

	public void setURL(String name, String url) {
		if (this.pics.containsKey(name)) {
			this.pics.get(name).setUrl(url);
		}
	}

}
