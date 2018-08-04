package net.mcbbs.cocoaui.pluginmessage.packages;

import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import net.mcbbs.cocoaui.pluginmessage.AbstractInPackage;

public class InJsonPackageDemo extends AbstractInPackage {
	JSONObject obj;

	public InJsonPackageDemo(byte[] data, Player sender, int id) {
		super(data, sender, id);
		this.readData();
	}

	private void readData() {
		this.obj = super.readJson();
	}

	public JSONObject getJson() {
		return obj;
	}
}
