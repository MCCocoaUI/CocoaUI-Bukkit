# CocoaUI 通讯管理器使用教程

------
## 发送部分
嗯顾名思义就是给玩家发哦是哪个数据包嘛


### 1. 先建个类吧
你需要让你的类extends [AbstractOutPackage][1]，嘛就是标记一下，发的时候方便，还继承了一些相关的函数，有兴趣呢可以点开名字去看源码。
开发组注意：请将所有的类建在[这里][2]，以Out或In开头。
然后我们需要为我们的包指定唯一的ID，ID需要发送端和接收端同步。
```Java
import net.mcbbs.cocoaui.pluginmessage.AbstractOutPackage;

public class OutDemo extends AbstractOutPackage{
	private static final int ID = 998;
	public OutDemo() {
		super(ID);
	}

}
```
建完后看起来是这样的。


### 2. 为我们的包写点内容：
super.getByteArrayDataOutput()方法可以获得字节缓存流，我们可以直接调用他的方法写入相关的内容，比如我写入了一个时间然后再写入了一个自定义的字符串。
```Java
import net.mcbbs.cocoaui.pluginmessage.AbstractOutPackage;

public class OutDemo extends AbstractOutPackage {
	private static final int ID = 998;
	String data;

	public OutDemo(String data) {
		super(ID);
		this.data = data;
		this.writeData();
	}

	private void writeData() {
		super.getByteArrayDataOutput().writeLong(System.currentTimeMillis());
		super.getByteArrayDataOutput().writeUTF(data);
	}

}
```
看起来就是这样了
### 3. 发送我们的包
单独给一个玩家发送
```Java
		CocoaUI.getPluginMessageManager().sendPackage(new OutDemo("测试"), p);
```	
给全体发送
```Java
		CocoaUI.getPluginMessageManager().sendAll(new OutDemo("测试"));
```	

未完待续
