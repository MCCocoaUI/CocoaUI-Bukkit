package net.mcbbs.mineviewer.test;

import net.mcbbs.mineviewer.pluginmessage.listener.PackageListener;
import net.mcbbs.mineviewer.pluginmessage.listener.PackageReceiveEvent;
import net.mcbbs.mineviewer.pluginmessage.listener.PackageSendEvent;
import net.mcbbs.mineviewer.pluginmessage.listener.Priority;
import org.bukkit.entity.Player;

import net.mcbbs.mineviewer.MineViewer;
import net.mcbbs.mineviewer.pluginmessage.packages.InputPackageDemo;
import net.mcbbs.mineviewer.pluginmessage.packages.OutputPackageDemo;

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
		MineViewer.getPluginMessageManager().sendPackage(new OutputPackageDemo(arg), p);
	}

}
