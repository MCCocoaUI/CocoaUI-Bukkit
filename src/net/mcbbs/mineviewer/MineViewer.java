package net.mcbbs.mineviewer;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import net.mcbbs.mineviewer.pluginmessage.Listener;
import net.mcbbs.mineviewer.pluginmessage.PluginMessageManager;

public class MineViewer extends JavaPlugin {
	private static File dataFolder;
	private static Logger log;
	private static PluginMessageManager pmm;

	public void onEnable() {
		this.initStatic();
		this.registerListeners();
	}

	private void registerListeners() {
		super.getServer().getMessenger().registerIncomingPluginChannel(this, "mineviewer", new Listener());
		super.getServer().getMessenger().registerOutgoingPluginChannel(this, "mineviewer");
	}

	private void initStatic() {
		MineViewer.dataFolder = super.getDataFolder();
		MineViewer.log = super.getLogger();
		MineViewer.pmm = new PluginMessageManager();
	}

	public static File getKDataFolder() {
		return MineViewer.dataFolder;
	}

	public static Logger getLog() {
		return MineViewer.log;
	}

	public static PluginMessageManager getPluginMessageManager() {
		return MineViewer.pmm;
	}

}
