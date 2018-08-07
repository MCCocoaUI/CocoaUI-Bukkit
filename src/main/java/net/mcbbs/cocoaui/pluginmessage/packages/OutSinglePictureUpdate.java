package net.mcbbs.cocoaui.pluginmessage.packages;

import net.mcbbs.cocoaui.managers.picturemanager.Picture;
import net.mcbbs.cocoaui.pluginmessage.AbstractOutPackage;

public class OutSinglePictureUpdate extends AbstractOutPackage {
	private final static int ID = 3;
	private Picture pic;
	private String plname;
	private int state;
	private String oldName;

//  1-update 2-rename 3-remove
	public OutSinglePictureUpdate(String plname, Picture pic, int state) {
		super(ID);
		this.pic = pic;
		this.plname = plname;
		this.state = state;
		this.writeData();
	}

	public OutSinglePictureUpdate(String plname, Picture pic, String oldName) {
		super(ID);
		this.pic = pic;
		this.plname = plname;
		this.oldName = oldName;
		this.state = 2;
		this.writeData();
	}

	private void writeData() {
		super.getByteArrayDataOutput().writeInt(state);
		if (this.state == 1) {
			super.getByteArrayDataOutput().writeUTF(plname);
			super.getByteArrayDataOutput().writeUTF(pic.getName());
			super.getByteArrayDataOutput().writeUTF(pic.getUrl());
			super.getByteArrayDataOutput().writeUTF(pic.getMD5());
			return;
		}
		if (state == 2) {
			super.getByteArrayDataOutput().writeUTF(plname);
			super.getByteArrayDataOutput().writeUTF(oldName);
			super.getByteArrayDataOutput().writeUTF(pic.getName());
			super.getByteArrayDataOutput().writeUTF(pic.getUrl());
			super.getByteArrayDataOutput().writeUTF(pic.getMD5());
		}
		if (state == 3) {
			super.getByteArrayDataOutput().writeUTF(plname);
			super.getByteArrayDataOutput().writeUTF(pic.getName());
		}

	}

}
