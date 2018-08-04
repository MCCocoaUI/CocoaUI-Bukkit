package net.mcbbs.cocoaui.test;

import net.mcbbs.cocoaui.pluginmessage.listener.PackageListener;
import net.mcbbs.cocoaui.pluginmessage.listener.PackageReceiveEvent;
import net.mcbbs.cocoaui.pluginmessage.listener.PackageSendEvent;
import net.mcbbs.cocoaui.pluginmessage.listener.Priority;
import org.bukkit.entity.Player;

import net.mcbbs.cocoaui.CocoaUI;
import net.mcbbs.cocoaui.pluginmessage.packages.InputPackageDemo;
import net.mcbbs.cocoaui.pluginmessage.packages.OutputPackageDemo;

public class PackageListenerDemo extends PackageListener {

	public Priority getPriority() {
		return Priority.LOW;
	}

	@Override
	public void onPackageReceive(PackageReceiveEvent e) {
		if (e.getID() == 1) {
			InputPackageDemo demo = (InputPackageDemo) e.getPackage();
			System.out.println(demo.getCustomString());
		}

	}

	@Override
	public void onPackageSend(PackageSendEvent e) {
		System.out.println("Package Send:" + e.getID());

	}

	public void sendCustomString(String arg, Player p) {
		CocoaUI.getPluginMessageManager().sendPackage(new OutputPackageDemo(arg), p);
	}

}
