package net.mcbbs.mineviewer.pluginmessage;

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

}
