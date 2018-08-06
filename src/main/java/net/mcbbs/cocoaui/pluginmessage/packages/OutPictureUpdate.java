package net.mcbbs.cocoaui.pluginmessage.packages;

import net.mcbbs.cocoaui.pluginmessage.AbstractOutPackage;

public class OutPictureUpdate extends AbstractOutPackage {
	private static int ID = 2;
	private String json;

	public OutPictureUpdate(String string) {
		super(ID);
		this.json = string;
		this.writeData();
	}

	private void writeData() {
		super.writeJson(this.json);
	}
	public int hashCode() {
		return 0;
	}
}
