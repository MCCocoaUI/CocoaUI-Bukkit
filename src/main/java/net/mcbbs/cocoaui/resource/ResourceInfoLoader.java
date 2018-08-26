package net.mcbbs.cocoaui.resource;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.mcbbs.cocoaui.utils.MD5Tool;

/**
 * 图片信息异步加载类 内部使用
 *
 * @author ChenJi
 *
 */
public class ResourceInfoLoader implements Callable<ResourceInfo> {

    private String url;
    private byte[] bytes;
    private int width = -1;
    private int height = -1;
    private String md5;
    private String name;
    private String pluginName;
    private boolean isPicture = false;

    public ResourceInfoLoader(String url, String name, String pluginName) {
        this.url = url;
        this.name = name;
        this.pluginName = pluginName;
    }

    public ResourceInfoLoader(String url, String name, String pluginName, boolean isPicture) {
        this.url = url;
        this.name = name;
        this.pluginName = pluginName;
        this.isPicture = isPicture;
    }

    @Override
    public ResourceInfo call() throws Exception {
        this.loadBytes();
        this.md5 = MD5Tool.md5(this.bytes);
        if (this.isPicture) {
            this.loadSize();
        }
        System.out.println("Resource " + this.name + " loaded:" + this.md5);
        return new ResourceInfo(this.url, this.md5, this.width, this.height, this.name, pluginName);
    }

    private void loadBytes() {
        try (InputStream in = new URI(this.url).toURL().openStream()) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            byte[] bs = new byte[16384];
            int len;
            while ((len = in.read(bs)) != -1) {
                out.write(bs, 0, len);
            }
            bytes = out.toByteArray();

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    private void loadSize() {
        BufferedImage img;
        try {
            img = ImageIO.read(new ByteArrayInputStream(this.bytes));
            this.width = img.getWidth();
            this.height = img.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
