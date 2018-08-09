package net.mcbbs.cocoaui.pluginmessage.packages;

import org.bukkit.entity.Player;

import net.mcbbs.cocoaui.pluginmessage.AbstractInPackage;

/**
 * 数据结构 <int> id<br>
 * <String> url<br>
 * <String> name<br>
 */
public class InImageUpdated extends AbstractInPackage {

    private String url;
    private String name;
    private Player p;

    public InImageUpdated(byte[] data, Player sender, int id) {
        super(data, sender, id);
        this.readData();
    }

    private void readData() {
        this.url = super.getByteArrayDataInput().readUTF();
        this.name = super.getByteArrayDataInput().readUTF();
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public Player getP() {
        return p;
    }

}
