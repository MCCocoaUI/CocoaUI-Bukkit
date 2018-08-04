package net.mcbbs.mineviewer.pluginmessage.listener;

import net.mcbbs.mineviewer.pluginmessage.AbstractOutPackage;

public class PackageSendEvent {
	boolean isCancelled;
	AbstractOutPackage pack;
	int id;

	public PackageSendEvent(AbstractOutPackage a) {
		this.pack = a;
		this.id = a.getID();
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

}
