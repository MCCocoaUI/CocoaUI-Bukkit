package net.mcbbs.cocoaui.utils.config;

import java.io.IOException;

import net.mcbbs.cocoaui.CocoaUI;

public class ConfigException extends Exception {

	String Message;
	Exception e;

	private static final long serialVersionUID = -3125946766812234248L;

	public ConfigException(IOException e, String Message) {
		this.e = e;
		this.Message = Message;
	}

	public final String getMessage() {
		return this.Message;
	}

	public void printStackTrace() {
		CocoaUI.getLog().warning("---------------CocoaUI 异常报告---------------");
		CocoaUI.getLog().warning("非常抱歉，CocoaUI 插件在运行时出现异常：IOException");
		CocoaUI.getLog().warning("异常原因：" + Message);
		CocoaUI.getLog().warning("以下是异常详情，请截图去论坛反馈，感谢合作。");
		super.printStackTrace();
		CocoaUI.getLog().warning("因为：");
		e.printStackTrace();
		CocoaUI.getLog().warning("-----------------------------------------------");
	}
}
