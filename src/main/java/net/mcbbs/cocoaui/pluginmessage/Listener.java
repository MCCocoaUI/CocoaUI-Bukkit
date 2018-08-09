package net.mcbbs.cocoaui.pluginmessage;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import net.mcbbs.cocoaui.CocoaUI;

public class Listener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {
        CocoaUI.getPluginMessageManager().receiveData(arg2, arg1);
    }

}
