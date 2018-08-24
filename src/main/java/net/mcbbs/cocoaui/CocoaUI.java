package net.mcbbs.cocoaui;

import java.io.File;
import java.util.logging.Logger;

import net.mcbbs.cocoaui.command.CommandHandler;
import net.mcbbs.cocoaui.listeners.PlayerListener;
import net.mcbbs.cocoaui.listeners.PluginMessageListener;
import net.mcbbs.cocoaui.managers.VerifyManager;
import net.mcbbs.cocoaui.managers.picturemanager.Picture;
import net.mcbbs.cocoaui.managers.picturemanager.PicturesManager;
import net.mcbbs.cocoaui.plugin.CocoaPluginManager;
import net.mcbbs.cocoaui.pluginmessage.Listener;
import net.mcbbs.cocoaui.pluginmessage.PluginMessageManager;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public class CocoaUI extends JavaPlugin {

    public CocoaUI() {
        CocoaUI.cocoaPluginManager = new CocoaPluginManager();
    }

    private static File dataFolder;
    private static Logger log;
    private static PluginMessageManager pluginMessageManager;
    private static CommandHandler commandHandler;
    private static PicturesManager picturesManager;
    private static VerifyManager verifyManager;
    private static CocoaPluginManager cocoaPluginManager;

    public void onEnable() {
        ConfigurationSerialization.registerClass(Picture.class);
        this.initStatic();
        this.registerListeners();
    }

    public void onDisable() {
        picturesManager.onDisable();
        verifyManager.onDisable();
        picturesManager.onDisable();
    }

    private void registerListeners() {
        super.getServer().getMessenger().registerIncomingPluginChannel(this, "CocoaUI", new Listener());
        super.getServer().getMessenger().registerOutgoingPluginChannel(this, "CocoaUI");
        super.getCommand("CocoaUI").setExecutor(commandHandler);
        super.getCommand("cui").setExecutor(commandHandler);
        new PluginMessageListener();
        super.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

    }

    private void initStatic() {

        CocoaUI.dataFolder = super.getDataFolder();
        CocoaUI.log = super.getLogger();
        CocoaUI.pluginMessageManager = new PluginMessageManager();
        CocoaUI.commandHandler = new CommandHandler();
        CocoaUI.picturesManager = new PicturesManager();
        picturesManager.init();
        CocoaUI.verifyManager = new VerifyManager();

    }

    public static File getKDataFolder() {
        return CocoaUI.dataFolder;
    }

    public static Logger getLog() {
        return CocoaUI.log;
    }

    public static PluginMessageManager getPluginMessageManager() {
        return CocoaUI.pluginMessageManager;
    }

    public static CommandHandler getCommandHandler() {
        return CocoaUI.commandHandler;
    }

    public static PicturesManager getPicturesManager() {
        return CocoaUI.picturesManager;
    }

    public static VerifyManager getVerfiyManager() {
        return CocoaUI.verifyManager;
    }

    public static boolean isFinish() {
        return CocoaUI.picturesManager.isFinish();
    }

    public static CocoaPluginManager getCocoaPluginManager() {
        return CocoaUI.cocoaPluginManager;
    }

}
