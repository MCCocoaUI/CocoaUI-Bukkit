package net.mcbbs.mineviewer;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class MineViewer extends JavaPlugin {
	private static File dataFolder;
	private static Logger log;

	public void onEnable() {
		MineViewer.dataFolder = super.getDataFolder();
		MineViewer.log = super.getLogger();
	}

	public static File getKDataFolder() {
		return MineViewer.dataFolder;
	}

	public static Logger getLog() {
		return MineViewer.log;
	}

}
