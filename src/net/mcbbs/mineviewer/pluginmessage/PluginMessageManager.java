package net.mcbbs.mineviewer.pluginmessage;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.TreeSet;

import org.bukkit.entity.Player;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.mcbbs.mineviewer.pluginmessage.listener.PackageListener;
import net.mcbbs.mineviewer.pluginmessage.listener.PackageReceiveEvent;

public class PluginMessageManager {
	private Map<Integer, Class<? extends AbstractInPackage>> classes = Maps.newHashMap();
	private TreeSet<PackageListener> listeners = Sets.newTreeSet();

	public void receiveData(byte[] data, Player p) {
		ByteArrayDataInput in = ByteStreams.newDataInput(data);
		int i = in.readInt();
		AbstractInPackage pack = this.getInstance(i, p, data);
		if (pack == null) {
			return;
		}
		this.dealInPackage(pack);

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
		PackageReceiveEvent e  = new PackageReceiveEvent(pack);
		for(PackageListener p:this.listeners) {
			p.onPackageReceive(e);
		}
	}

}
