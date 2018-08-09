package net.mcbbs.cocoaui;

import java.io.File;
import java.util.logging.Logger;

import net.mcbbs.cocoaui.command.CommandHandler;
import net.mcbbs.cocoaui.listeners.PlayerListener;
import net.mcbbs.cocoaui.listeners.PluginMessageListener;
import net.mcbbs.cocoaui.managers.VerifyManager;
import net.mcbbs.cocoaui.managers.picturemanager.Picture;
import net.mcbbs.cocoaui.managers.picturemanager.PicturesManager;
import net.mcbbs.cocoaui.pluginmessage.Listener;
import net.mcbbs.cocoaui.pluginmessage.PluginMessageManager;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public class CocoaUI extends JavaPlugin {
	private static File dataFolder;
	private static Logger log;
	private static PluginMessageManager pmm;
	private static CommandHandler ch;
	private static PicturesManager pm;
	private static VerifyManager vm;

	public void onEnable() {
		ConfigurationSerialization.registerClass(Picture.class);
		this.initStatic();
		this.registerListeners();
	}

	public void onDisable() {
		pm.save();
		vm.onDisable();
		pm.onDisable();
	}

	private void registerListeners() {
		super.getServer().getMessenger().registerIncomingPluginChannel(this, "CocoaUI", new Listener());
		super.getServer().getMessenger().registerOutgoingPluginChannel(this, "CocoaUI");
		super.getCommand("CocoaUI").setExecutor(ch);
		super.getCommand("cui").setExecutor(ch);
		new PluginMessageListener();
		super.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

	}

	private void initStatic() {
		CocoaUI.dataFolder = super.getDataFolder();
		CocoaUI.log = super.getLogger();
		CocoaUI.pmm = new PluginMessageManager();
		CocoaUI.ch = new CommandHandler();
		CocoaUI.pm = new PicturesManager();
		pm.init();
		CocoaUI.vm = new VerifyManager();
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

	public static VerifyManager getVerfiyManager() {
		return CocoaUI.vm;
	}

	public static boolean isFinish() {
		return pm.isFinish();
	}

}
