/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */

package net.mcbbs.mineviewer.packets;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 */
public class PacketManager {
    public static final int MAGIC_VALUE = 0x64775465;
    
    public static byte[] encodePacket(Packet<?> p){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeInt(PacketManager.MAGIC_VALUE);
        out.writeInt(p.getPacketId());
        p.write(out);
        return out.toByteArray();
    }
    
    
}
