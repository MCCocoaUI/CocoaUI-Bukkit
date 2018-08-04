/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package net.mcbbs.mineviewer.packets;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 */
public interface Packet<T> {


    public int getPacketId();

    public byte[] getBytes();

    public T getData();
}
