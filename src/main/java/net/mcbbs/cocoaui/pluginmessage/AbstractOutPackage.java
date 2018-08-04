package net.mcbbs.cocoaui.pluginmessage;

import org.json.simple.JSONObject;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public abstract class AbstractOutPackage {

    private int id;
    private ByteArrayDataOutput out;

    public AbstractOutPackage(int id) {
        out = ByteStreams.newDataOutput();
        this.dealId();
    }

    protected ByteArrayDataOutput getByteArrayDataOutput() {
        return out;
    }

    public final PackageType getType() {
        return PackageType.OUT;
    }

    private void dealId() {
        out.writeInt(id);
    }

    public final int getID() {
        return id;
    }

    protected byte[] getBytes() {
        return out.toByteArray();
    }

    protected final void writeJson(JSONObject json) {
        this.out.writeUTF("Json");
        this.out.writeUTF(json.toJSONString());
    }

}
