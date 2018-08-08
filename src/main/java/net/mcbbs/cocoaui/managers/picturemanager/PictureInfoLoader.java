package net.mcbbs.cocoaui.managers.picturemanager;

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

public class PictureInfoLoader implements Callable<PictureInfo> {
	private String url;
	private byte[] bytes;
	private int width;
	private int height;
	private String md5;

	public PictureInfoLoader(String url) {
		this.url = url;
	}

	@Override
	public PictureInfo call() throws Exception {
		this.loadBytes();
		this.md5 = MD5Tool.md5(this.bytes);
		this.loadSize();
		return new PictureInfo(this.url, this.md5, this.width, this.height);
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
