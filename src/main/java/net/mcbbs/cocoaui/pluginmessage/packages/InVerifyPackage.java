package net.mcbbs.cocoaui.pluginmessage.packages;

import net.mcbbs.cocoaui.pluginmessage.AbstractInPackage;
import org.bukkit.entity.Player;

public class InVerifyPackage extends AbstractInPackage {
	private final static int ID = 1;
	private String version;

	public InVerifyPackage(byte[] data, Player sender) {
		super(data, sender, ID);
		this.readData();
	}

	private void readData() {
		this.version = super.getByteArrayDataInput().readUTF();
	}

	public String getVersion() {
		return this.version;
	}

}
