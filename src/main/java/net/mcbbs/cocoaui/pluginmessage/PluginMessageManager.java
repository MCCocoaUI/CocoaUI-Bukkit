package net.mcbbs.cocoaui.pluginmessage;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.TreeSet;

import net.mcbbs.cocoaui.CocoaUI;
import net.mcbbs.cocoaui.pluginmessage.listener.PackageListener;
import net.mcbbs.cocoaui.pluginmessage.listener.PackageReceiveEvent;
import net.mcbbs.cocoaui.pluginmessage.listener.PackageSendEvent;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.mcbbs.cocoaui.pluginmessage.packages.InputPackageDemo;

public class PluginMessageManager {
	private Map<Integer, Class<? extends AbstractInPackage>> classes = Maps.newHashMap();
	private TreeSet<PackageListener> listeners = Sets.newTreeSet();
	public PluginMessageManager() {
		this.init();
	}
	public void receiveData(byte[] data, Player p) {
		ByteArrayDataInput in = ByteStreams.newDataInput(data);
		int i = in.readInt();	//判断数据类型
		AbstractInPackage pack = this.getInstance(i, p, data);		//根据数据类型获取数据包的实例
		if (pack == null) {
			return;
		}
		this.dealInPackage(pack);//放入事件系统处理。
	}

	public void sendPackage(AbstractOutPackage out, Player p) {
		PackageSendEvent e = new PackageSendEvent(out, p);
		for (PackageListener listener : this.listeners) {
			listener.onPackageSend(e);
		}
		if (e.isCancelled()) {
			return;
		}
		p.sendPluginMessage(CocoaUI.getPlugin(CocoaUI.class), "CocoaUI", out.getBytes());
	}

	public void registerListener(PackageListener listener) {
		this.listeners.add(listener);
	}

	public void removeListener(PackageListener listener) {
		this.listeners.remove(listener);
	}

	public void registerPackage(int id, Class<? extends AbstractInPackage> clazz) {
		this.classes.put(id, clazz);
	}

	public void removePackage(int id) {
		classes.remove(id);
	}

	public AbstractInPackage getInstance(int i, Player p, byte[] data) {
		if (!this.classes.containsKey(i)) {
			return null;
		}
		try {
			return (AbstractInPackage) this.classes.get(i).getConstructors()[0].newInstance(data, p, i);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void dealInPackage(AbstractInPackage pack) {
		PackageReceiveEvent e = new PackageReceiveEvent(pack);
		for (PackageListener p : this.listeners) {
			p.onPackageReceive(e);
		}
	}
	
	private void init() {
		this.registerPackage(1, InputPackageDemo.class);
	}

}
