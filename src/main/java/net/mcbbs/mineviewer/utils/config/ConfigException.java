package net.mcbbs.mineviewer.utils.config;

import java.io.IOException;

import net.mcbbs.mineviewer.MineViewer;

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
		MineViewer.getLog().warning("---------------MineViewer 异常报告---------------");
		MineViewer.getLog().warning("非常抱歉，MineViewer 插件在运行时出现异常：IOException");
		MineViewer.getLog().warning("异常原因：" + Message);
		MineViewer.getLog().warning("以下是异常详情，请截图去论坛反馈，感谢合作。");
		super.printStackTrace();
		MineViewer.getLog().warning("因为：");
		e.printStackTrace();
		MineViewer.getLog().warning("-----------------------------------------------");
	}
}