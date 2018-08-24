package net.mcbbs.cocoaui.listeners;

import net.mcbbs.cocoaui.CocoaUI;
import net.mcbbs.cocoaui.pluginmessage.listener.PackageListener;
import net.mcbbs.cocoaui.pluginmessage.listener.PackageReceiveEvent;
import net.mcbbs.cocoaui.pluginmessage.listener.PackageSendEvent;
import net.mcbbs.cocoaui.pluginmessage.packages.InPictureChooserBack;
import net.mcbbs.cocoaui.pluginmessage.packages.InVerifyPackage;

public class PluginMessageListener extends PackageListener {

    public PluginMessageListener() {
        CocoaUI.getPluginMessageManager().registerListener(this);
    }

    @Override
    public void onPackageReceive(PackageReceiveEvent e) {
        switch (e.getID()) {
            case 1:
                InVerifyPackage pack = (InVerifyPackage) e.getPackage();
                CocoaUI.getLog().info(e.getPlayer().getName() + "通过了验证，装载的CocoaUIMod版本是" + pack.getVersion());
                CocoaUI.getVerfiyManager().receiveVerify(e.getPlayer());
                CocoaUI.getPicturesManager().sendUpdatePackage(e.getPlayer());
                return;
            case 5:
                InPictureChooserBack pack1 = (InPictureChooserBack) e.getPackage();
                CocoaUI.getPicturesManager().setURL(pack1.getPlayer(), pack1.getURL());
                pack1.getPlayer().sendMessage("Receive URL:" + pack1.getURL());
                return;
        }

    }

    @Override
    public void onPackageSend(PackageSendEvent e) {

    }

}
