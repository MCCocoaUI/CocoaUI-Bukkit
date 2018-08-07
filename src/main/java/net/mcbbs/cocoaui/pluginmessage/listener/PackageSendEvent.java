package net.mcbbs.cocoaui.pluginmessage.listener;

import org.bukkit.entity.Player;

import net.mcbbs.cocoaui.pluginmessage.AbstractOutPackage;

public class PackageSendEvent {
	boolean isCancelled = false;
	AbstractOutPackage pack;
	Player p;
	int id;

	public PackageSendEvent(AbstractOutPackage a) {
		this.pack = a;
		this.id = a.getID();
	}

	public PackageSendEvent(AbstractOutPackage out, Player p) {
		this.p = p;
		this.pack = out;
	}

	public void setCancelled(boolean arg) {
		this.isCancelled = arg;
	}

	public boolean isCancelled() {
		return this.isCancelled;
	}

	public int getID() {
		return this.id;
	}

	public Player getPlayer() {
		return this.p;
	}
	public AbstractOutPackage getPackage() {return pack;}

}
