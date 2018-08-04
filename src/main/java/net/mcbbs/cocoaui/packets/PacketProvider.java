/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */

package net.mcbbs.cocoaui.packets;

import com.google.common.io.ByteArrayDataInput;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 */
public abstract class PacketProvider<T extends Packet>{
    public static final Map<Integer, PacketProvider<? extends Packet>> PacketProviders = new HashMap<>();
    
    public abstract T readPacket(ByteArrayDataInput data);
    
    public abstract T createPacket();
    
    public abstract int getPacketId();
    
    public static  <T extends Packet> PacketProvider<T> getPacketProvider(int id){
        return (PacketProvider<T>) PacketProviders.get(id);
    }
    
    public static <T extends Packet> PacketProvider getPacketProvider(Class<? extends PacketProvider<T>> c){
        for (PacketProvider<?> pp : PacketProviders.values()) {
            if(pp.getClass() == c){
                return pp;
            }
        }
        return null;
    }
    
    public static void registerPacketProvider(PacketProvider pp){
        PacketProviders.put(pp.getPacketId(), pp);
    }
}
