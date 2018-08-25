package net.mcbbs.cocoaui.pluginmessage.packages;

import net.mcbbs.cocoaui.pluginmessage.AbstractOutPackage;

/**
 * 图片信息发送截止包
 *
 * @author ChenJi
 *
 */
public class OutResourceUpdateSent extends AbstractOutPackage {

    public static final int ID = 4;

    public OutResourceUpdateSent() {
        super(ID);
    }
}
