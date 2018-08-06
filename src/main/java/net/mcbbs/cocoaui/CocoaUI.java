package net.mcbbs.cocoaui;

import java.io.File;
import java.util.logging.Logger;

import net.mcbbs.cocoaui.command.CommandHandler;
import net.mcbbs.cocoaui.managers.picturemanager.PicturesManager;
import net.mcbbs.cocoaui.pluginmessage.Listener;
import net.mcbbs.cocoaui.pluginmessage.PluginMessageManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CocoaUI extends JavaPlugin {
	private static File dataFolder;
	private static Logger log;
	private static PluginMessageManager pmm;
	private static CommandHandler ch;
	private static PicturesManager pm;

	public void onEnable() {
		this.initStatic();
		this.registerListeners();
	}

	private void registerListeners() {
		super.getServer().getMessenger().registerIncomingPluginChannel(this, "cocoaui", new Listener());
		super.getServer().getMessenger().registerOutgoingPluginChannel(this, "cocoaui");
		super.getCommand("CocoaUI").setExecutor(ch);
		super.getCommand("cui").setExecutor(ch);
	}

	private void initStatic() {
		CocoaUI.dataFolder = super.getDataFolder();
		CocoaUI.log = super.getLogger();
		CocoaUI.pmm = new PluginMessageManager();
		CocoaUI.ch = new CommandHandler();
		CocoaUI.pm = new PicturesManager();
	}

	public static File getKDataFolder() {
		return CocoaUI.dataFolder;
	}

	public static Logger getLog() {
		return CocoaUI.log;
	}

	public static PluginMessageManager getPluginMessageManager() {
		return CocoaUI.pmm;
	}

	public static CommandHandler getCommandHandler() {
		return CocoaUI.ch;
	}

	public static PicturesManager getPicturesManager() {
		return CocoaUI.pm;
	}

}
