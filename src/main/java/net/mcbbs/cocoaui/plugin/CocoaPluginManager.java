package net.mcbbs.cocoaui.plugin;

import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.mcbbs.cocoaui.CocoaUI;

public class CocoaPluginManager {
	private Map<String, CocoaPlugin> plugins = Maps.newHashMap();
	private Set<String> allPlugins = Sets.newHashSet();

	public CocoaPluginManager() {
		for (Plugin p : Bukkit.getServer().getPluginManager().getPlugins()) {
			if (p.getDescription().getDepend().contains("CocoaUI")) {
				this.allPlugins.add(p.getName());
			}
		}
	}

	public void onPluginEnable(String name) {
		if (this.allPlugins.contains(name)) {
			this.allPlugins.remove(name);
		}
		if (this.allPlugins.isEmpty()) {
			CocoaUI.getPicturesManager().onServerDone();
		}
	}
	public void registerPlugin(CocoaPlugin plugin) {
		this.plugins.put(plugin.getName(), plugin);
	}

	public boolean contains(String name) {
		System.out.println(this.plugins);
		return this.plugins.containsKey(name);
	}
}
