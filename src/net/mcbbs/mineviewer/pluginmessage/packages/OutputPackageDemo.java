package net.mcbbs.mineviewer.pluginmessage.packages;

import net.mcbbs.mineviewer.pluginmessage.AbstractOutPackage;

public class OutputPackageDemo extends AbstractOutPackage{
	private static int ID = 1;
	private String customString;
	
	public OutputPackageDemo(String customString) {
		super(ID);
		this.customString = customString;
		this.writeData();
	}
	
	private void writeData() {
		super.getByteArrayDataOutput().writeUTF(this.customString);
	}
	
}
