package net.mcbbs.cocoaui.pluginmessage;

import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
/**
 * 抽象输入包
 * @author ChenJi
 *
 */
public abstract class AbstractInPackage {

	private ByteArrayDataInput in;
	protected byte[] orgianData;
	protected Player sender;
	protected int id;
	/**
	 * 构造函数
	 * @param data 原始数据
	 * @param sender 发送者
	 * @param id 包的ID
	 */
	public AbstractInPackage(byte[] data, Player sender, int id) {
		this.orgianData = data;
		this.sender = sender;
		this.id = id;
		in = ByteStreams.newDataInput(data);
		in.readInt();
	}

	/**
	 * 获得字节缓冲流
	 * 
	 * @return ByteArrayDataInput
	 */
	protected ByteArrayDataInput getByteArrayDataInput() {
		return this.in;
	}

	/**
	 * 获得包的原始数据
	 * 
	 * @return data
	 */
	public final byte[] getOrginanData() {
		return this.orgianData;
	}

	/**
	 * 获得这个包的来源玩家
	 * 
	 * @return 玩家
	 */
	public final Player getPlayer() {
		return this.sender;
	}

	/**
	 * 获取这个包的ID
	 * 
	 * @return id
	 */
	public final int getID() {
		return this.id;
	}

	/**
	 * 获取这个包的类型（IN or Out）
	 * 
	 * @return PackageType
	 */
	public final PackageType getType() {
		return PackageType.IN;
	}

	/**
	 * 读取Json
	 * 
	 * @return
	 */
	protected JSONObject readJson() {
		if (!"Json".equalsIgnoreCase(in.readUTF())) {
			return null;
		}
		String json = in.readUTF();
		JSONObject jsonobj = (JSONObject) JSONValue.parse(json);
		return jsonobj;
	}

}
