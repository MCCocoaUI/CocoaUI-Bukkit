/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package net.mcbbs.cocoaui.packets;

import java.util.function.Consumer;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 */
public enum Send2Type {
    Bukkit, Forge, Sponge, Android;
    private Consumer<Packet> SendFunction = (p) -> {
        throw new UnsupportedOperationException();
    };

    public Consumer<Packet> getSendFunction() {
        return SendFunction;
    }

    public void setSendFunction(Consumer<Packet> SendFunction) {
        this.SendFunction = SendFunction;
    }

}
