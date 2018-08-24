package net.mcbbs.cocoaui.pluginmessage.packages;

import net.mcbbs.cocoaui.pluginmessage.AbstractOutPackage;

public class OutDemo extends AbstractOutPackage {

    private static final int ID = 998;
    String data;

    public OutDemo(String data) {
        super(ID);
        this.data = data;
        this.writeData();
    }

    private void writeData() {
        super.getByteArrayDataOutput().writeLong(System.currentTimeMillis());
        super.getByteArrayDataOutput().writeUTF(data);
    }

}
