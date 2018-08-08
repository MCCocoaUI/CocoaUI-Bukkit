package net.mcbbs.cocoaui.managers.picturemanager;

public class PictureInfo {
	public PictureInfo(String url, String md5, int width, int height) {
		super();
		this.url = url;
		this.md5 = md5;
		this.width = width;
		this.height = height;
	}
	String url;
	String md5;
	int width;
	int height;
}
