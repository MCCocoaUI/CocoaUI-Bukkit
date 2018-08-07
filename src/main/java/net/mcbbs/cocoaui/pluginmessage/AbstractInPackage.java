package net.mcbbs.cocoaui.pluginmessage;

import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public abstract class AbstractInPackage {

    private ByteArrayDataInput in;
    protected byte[] orgianData;
    protected Player sender;
    protected int id;

    public AbstractInPackage(byte[] data, Player sender, int id) {
        this.orgianData = data;
        this.sender = sender;
        this.id = id;
        in = ByteStreams.newDataInput(data);
        in.readInt();
    }

    protected ByteArrayDataInput getByteArrayDataInput() {
        return this.in;
    }

    public final byte[] getOrginanData() {
        return this.orgianData;
    }

    public final Player getPlayer() {
        return this.sender;
    }

    public final int getID() {
        return this.id;
    }

    public final PackageType getType() {
        return PackageType.IN;
    }

    protected JSONObject readJson() {
        if (!"Json".equalsIgnoreCase(in.readUTF())) {
            return null;
        }     
        String json = in.readUTF();
        JSONObject jsonobj = (JSONObject) JSONValue.parse(json);
        return jsonobj;
    }

}
