package net.mcbbs.cocoaui.pluginmessage.packages;

import net.mcbbs.cocoaui.pluginmessage.AbstractOutPackage;

/**
 * 图片信息更新包
 * 
 * @author ChenJi
 *
 */
public class OutPictureUpdate extends AbstractOutPackage {

	private final static int ID = 2;
	private String json;

	public OutPictureUpdate(String string) {
		super(ID);
		this.json = string;
		this.writeData();
	}

	private void writeData() {
		super.writeJson(this.json);
	}

	public String toString() {
		return this.json;
	}
}
