package net.mcbbs.cocoaui.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import net.mcbbs.cocoaui.CocoaUI;
import net.mcbbs.cocoaui.managers.picturemanager.PluginPicturesManager;

public abstract class CocoaPlugin extends JavaPlugin {
	String pluginName;

	public CocoaPlugin(String pluginName) throws CocoaPluginException {
		this.pluginName = pluginName;
		if (CocoaUI.getCocoaPluginManager().contains(pluginName)) {
			throw new CocoaPluginException("名叫" + this.pluginName + "的插件已经存在了");
		}
		CocoaUI.getPicturesManager().registerPlugin(this.pluginName);
	}

	public final String getPluginName() {
		return this.pluginName;
	}

	public PluginPicturesManager getPluginPicturesManager() {
		return CocoaUI.getPicturesManager().getPluginPictureManager(this.pluginName);
	}
}
