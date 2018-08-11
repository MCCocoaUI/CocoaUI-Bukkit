package net.mcbbs.cocoaui.pluginmessage;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.TreeSet;

import net.mcbbs.cocoaui.CocoaUI;
import net.mcbbs.cocoaui.pluginmessage.listener.PackageListener;
import net.mcbbs.cocoaui.pluginmessage.listener.PackageReceiveEvent;
import net.mcbbs.cocoaui.pluginmessage.listener.PackageSendEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.mcbbs.cocoaui.pluginmessage.packages.InPictureChooserBack;
import net.mcbbs.cocoaui.pluginmessage.packages.InVerifyPackage;

/**
 * 插件消息管理器
 * 
 * @author ChenJi
 *
 */
public class PluginMessageManager {
	private Map<Integer, Class<? extends AbstractInPackage>> classes = Maps.newHashMap();
	private TreeSet<PackageListener> listeners = Sets.newTreeSet();

	/**
	 * 构造函数
	 */
	public PluginMessageManager() {
		this.init();
	}

	/**
	 * 从PluginMessageListener接收原始数据
	 * 
	 * @param data 数据
	 * @param p    玩家
	 */
	public void receiveData(byte[] data, Player p) {
		ByteArrayDataInput in = ByteStreams.newDataInput(data);
		int i = in.readInt();
		AbstractInPackage pack = this.getInstance(i, p, data);
		if (pack == null) {
			return;
		}
		this.dealInPackage(pack);
	}

	/**
	 * 为某个玩家单独发送一个数据包
	 * 
	 * @param out 数据包
	 * @param p   玩家
	 */
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

	/**
	 * 注册监听器
	 * 
	 * @param listener 监听器实例
	 */
	public void registerListener(PackageListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * 删除监听器
	 * 
	 * @param listener 监听器实例
	 */
	public void removeListener(PackageListener listener) {
		this.listeners.remove(listener);
	}

	/**
	 * 注册一个输入包
	 * 
	 * @param id    包ID
	 * @param clazz 包的Class
	 */
	public void registerPackage(int id, Class<? extends AbstractInPackage> clazz) {
		this.classes.put(id, clazz);
	}

	/**
	 * 通过id删除一个包
	 * 
	 * @param id 包的ID
	 */
	public void removePackage(int id) {
		classes.remove(id);
	}

	/**
	 * 根据数据和id构造一个对应的包
	 * 
	 * @param i    ID
	 * @param p    玩家
	 * @param data 数据
	 * @return 抽象输入包
	 */
	public AbstractInPackage getInstance(int i, Player p, byte[] data) {
		if (!this.classes.containsKey(i)) {
			return null;
		}
		try {
			return (AbstractInPackage) this.classes.get(i).getConstructors()[0].newInstance(data, p);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void dealInPackage(AbstractInPackage pack) {
		PackageReceiveEvent e = new PackageReceiveEvent(pack);
		for (PackageListener p : this.listeners) {
			p.onPackageReceive(e);
		}
	}

	private void init() {
		this.registerPackage(1, InVerifyPackage.class);
		this.registerPackage(5, InPictureChooserBack.class);
	}

	/**
	 * 广播发送一个数据包
	 * 
	 * @param pack 数据包
	 */
	public void sendAll(AbstractOutPackage pack) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			this.sendPackage(pack, p);
		}
	}

}
