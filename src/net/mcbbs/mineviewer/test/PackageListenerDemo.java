package net.mcbbs.mineviewer.test;

import net.mcbbs.mineviewer.pluginmessage.listener.PackageListener;
import net.mcbbs.mineviewer.pluginmessage.listener.PackageReceiveEvent;
import net.mcbbs.mineviewer.pluginmessage.listener.PackageSendEvent;
import net.mcbbs.mineviewer.pluginmessage.listener.Priority;

public class PackageListenerDemo extends PackageListener {

	public Priority getPriority() {
		return Priority.LOW;
	}

	@Override
	public void onPackageReceive(PackageReceiveEvent e) {
		System.out.println("Receive Package:" + e.getID());

	}

	@Override
	public void onPackageSend(PackageSendEvent e) {
		System.out.println("Package Send:" + e.getID());

	}

}
