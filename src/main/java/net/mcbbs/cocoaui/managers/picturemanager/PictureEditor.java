package net.mcbbs.cocoaui.managers.picturemanager;

import java.util.UUID;

/**
 * 图片编辑器记录类 内部使用
 *
 * @author ChenJi
 *
 */
public class PictureEditor {

    public PictureEditor(UUID p, String pluginName, String name) {
        this.p = p;
        this.pluginName = pluginName;
        this.name = name;
    }

    UUID p;
    String pluginName;
    String name;

    public UUID getPlayer() {
        return p;
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getName() {
        return name;
    }

}
