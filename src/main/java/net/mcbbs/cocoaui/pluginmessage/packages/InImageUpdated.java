package net.mcbbs.cocoaui.pluginmessage.packages;

import org.bukkit.entity.Player;

import net.mcbbs.cocoaui.pluginmessage.AbstractInPackage;

/**
 * 图片更新结果接收包
 * 
 * @author ChenJi
 *
 */
public class InImageUpdated extends AbstractInPackage {

	private String url;
	private String name;
	private Player p;

	public InImageUpdated(byte[] data, Player sender, int id) {
		super(data, sender, id);
		this.readData();
	}

	private void readData() {
		this.url = super.getByteArrayDataInput().readUTF();
		this.name = super.getByteArrayDataInput().readUTF();
	}

	public String getUrl() {
		return url;
	}

	public String getName() {
		return name;
	}

	public Player getP() {
		return p;
	}

}
