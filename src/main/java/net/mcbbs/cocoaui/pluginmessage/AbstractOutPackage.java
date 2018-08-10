package net.mcbbs.cocoaui.pluginmessage;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

/**
 * 抽象输出包
 * 
 * @author ChenJi
 *
 */
public abstract class AbstractOutPackage {

	private int id;
	private ByteArrayDataOutput out;

	/**
	 * 构造函数
	 * 
	 * @param id 包的ID
	 */
	public AbstractOutPackage(int id) {
		out = ByteStreams.newDataOutput();
		this.id = id;
		this.dealId();
	}

	/**
	 * 获得字节缓冲流
	 * 
	 * @return ByteArrayDataInput
	 */
	protected ByteArrayDataOutput getByteArrayDataOutput() {
		return out;
	}

	/**
	 * 获得字节缓冲流
	 * 
	 * @return ByteArrayDataInput
	 */
	public final PackageType getType() {
		return PackageType.OUT;
	}

	private void dealId() {
		out.writeInt(id);
	}

	/**
	 * 获取这个包的ID
	 * 
	 * @return id
	 */
	public final int getID() {
		return id;
	}

	/**
	 * 获取这个包即将发出去的字节
	 * 
	 * @return bytes
	 */
	protected byte[] getBytes() {
		return out.toByteArray();
	}

	/**
	 * 写入json
	 * 
	 * @param json
	 */
	protected final void writeJson(String json) {
		this.out.writeUTF("Json");
		this.out.writeUTF(json);
	}

}
