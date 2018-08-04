package net.mcbbs.mineviewer.pluginmessage.packages;

import net.mcbbs.mineviewer.pluginmessage.AbstractInPackage;
import org.bukkit.entity.Player;

public class InputPackageDemo extends AbstractInPackage {
	private static int ID = 1;
	private String customString;

	public InputPackageDemo(byte[] data, Player sender) {
		super(data, sender, ID);
		this.readData();
	}

	private void readData() {
		this.customString = super.getByteArrayDataInput().readUTF();
	}

	public String getCustomString() {
		return this.customString;
	}

}
