package net.mcbbs.mineviewer.pluginmessage.listener;

import org.bukkit.entity.Player;

import net.mcbbs.mineviewer.pluginmessage.AbstractInPackage;

public class PackageReceiveEvent {

	boolean isCancelled;
	AbstractInPackage pack;
	int id;
	Player p;

	public PackageReceiveEvent(AbstractInPackage a) {
		this.pack = a;
		this.id = a.getID();
		this.p = a.getPlayer();
	}

	public void setCancelled(boolean arg) {
		this.isCancelled = arg;
	}

	public boolean isCancelled(boolean arg) {
		return this.isCancelled;
	}

	public int getID() {
		return this.id;
	}

	public Player getPlayer() {
		return this.p;
	}

}
