package net.mcbbs.cocoaui.plugin;

import net.mcbbs.cocoaui.CocoaUI;

public class CocoaPluginException extends Exception{

	private static final long serialVersionUID = 6347718687625763341L;
	String Message;
	
	public CocoaPluginException(String Message) {
		this.Message = Message;
	}
	
	public final String getMessage() {
		return this.Message;
	}

	public void printStackTrace() {
		CocoaUI.getLog().warning("---------------CocoaUI 异常报告---------------");
		CocoaUI.getLog().warning("非常抱歉，CocoaUI 插件在运行时出现异常：CocoaPluginException");
		CocoaUI.getLog().warning("异常原因：" + Message);
		CocoaUI.getLog().warning("以下是异常详情，请截图去论坛反馈，感谢合作。");
		super.printStackTrace();
		CocoaUI.getLog().warning("-----------------------------------------------");
	}
}
