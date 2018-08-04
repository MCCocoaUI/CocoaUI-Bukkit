/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */

package net.mcbbs.mineviewer.packets;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 */
public class Package {
    private static final String PACKAGE_END= "PACKAGE_END";
    
    private List<Packet<?>> Packets;
    
    public Package(){
        Packets = new ArrayList<>();
    }
    
    public Package(byte data[]){
        Packets = new ArrayList<>();
        ByteArrayDataInput in = ByteStreams.newDataInput(data);
        //TODO
    }
    
    public byte[] toBytes(){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        //TODO: 写入数据头 找个人来艹这鬼事情
        for (Packet<?> p : Packets) {
            out.writeInt(p.getPacketId());
            out.write(p.getBytes());
        }
        out.writeUTF(PACKAGE_END);
        return out.toByteArray();
    }

    public List<Packet<?>> getPackets() {
        return Packets;
    }
    
    
}
