package net.mcbbs.cocoaui.plugin;

import java.util.Map;

import com.google.common.collect.Maps;

public class CocoaPluginManager {
	private Map<String, CocoaPlugin> plugins = Maps.newHashMap();

	public boolean contains(String name) {
		return !this.plugins.containsKey(name);
	}
}
