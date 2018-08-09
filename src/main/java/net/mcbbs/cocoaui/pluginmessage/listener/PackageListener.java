package net.mcbbs.cocoaui.pluginmessage.listener;

public abstract class PackageListener implements Comparable<PackageListener> {

    public abstract void onPackageReceive(PackageReceiveEvent e);

    public abstract void onPackageSend(PackageSendEvent e);

    public Priority getPriority() {
        return Priority.MIDDLE;
    }

    public int compareTo(PackageListener e) {
        int a = e.getPriority().getValue();
        int b = this.getPriority().getValue();
        return (a > b) ? 1 : (a < b ? 0 : -1);

    }
}
