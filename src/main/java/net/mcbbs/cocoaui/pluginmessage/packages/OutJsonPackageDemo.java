package net.mcbbs.cocoaui.pluginmessage.packages;

import org.json.simple.JSONObject;

import net.mcbbs.cocoaui.pluginmessage.AbstractOutPackage;

public class OutJsonPackageDemo extends AbstractOutPackage {
	public OutJsonPackageDemo(JSONObject obj) {
		super(2);
		super.writeJson(obj);
	}
}
