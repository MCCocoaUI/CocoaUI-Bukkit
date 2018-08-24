package net.mcbbs.cocoaui.pluginmessage.packages;

import org.bukkit.entity.Player;

import net.mcbbs.cocoaui.pluginmessage.AbstractInPackage;

public class InPictureChooserBack extends AbstractInPackage {

    private static final int ID = 5;
    private String data;

    public InPictureChooserBack(byte[] data, Player sender) {
        super(data, sender, ID);
        this.data = super.getByteArrayDataInput().readUTF();
    }

    public String getURL() {
        return this.data;
    }

}
