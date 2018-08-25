package net.mcbbs.cocoaui.pluginmessage.packages;

import net.mcbbs.cocoaui.pluginmessage.AbstractOutPackage;
import net.mcbbs.cocoaui.resource.Resource;

/**
 * 单个图片更新包
 *
 * @author ChenJi
 *
 */
public class OutSingleResourceUpdate extends AbstractOutPackage {

    private final static int ID = 3;
    private Resource pic;
    private String plname;
    private int state;

//  1-update 2-rename 3-remove
    public OutSingleResourceUpdate(String plname, Resource pic, int state) {
        super(ID);
        this.pic = pic;
        this.plname = plname;
        this.state = state;
        this.writeData();
    }

    private void writeData() {
        super.getByteArrayDataOutput().writeInt(state);
        if (this.state == 1) {
            super.getByteArrayDataOutput().writeUTF(plname);
            super.getByteArrayDataOutput().writeUTF(pic.getName());
            super.getByteArrayDataOutput().writeUTF(pic.getUrl());
            return;
        }
        if (state == 3) {
            super.getByteArrayDataOutput().writeUTF(plname);
            super.getByteArrayDataOutput().writeUTF(pic.getName());
        }

    }

}
