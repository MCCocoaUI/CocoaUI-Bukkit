package net.mcbbs.cocoaui.listeners;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


import net.mcbbs.cocoaui.CocoaUI;


public class PlayerListener implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
	CocoaUI.getVerfiyManager().verifyPlayer(e.getPlayer());


		
	}
}
