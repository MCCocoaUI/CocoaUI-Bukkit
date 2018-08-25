package net.mcbbs.cocoaui.resource;

public enum ResourceType {
	VIDEO("video"), PICTURE("picture"), MUSIC("music"), FONT("font");
	private String type;

	private ResourceType(String type) {
		this.type = type;
	}

	public String toString() {
		return this.type;
	}

	public static ResourceType toResourceType(String typename) {
		switch (typename) {
		case "video":
			return VIDEO;
		case "picture":
			return PICTURE;
		case "music":
			return MUSIC;
		case "font":
			return FONT;
		default:
			return null;
		}
	}
}
