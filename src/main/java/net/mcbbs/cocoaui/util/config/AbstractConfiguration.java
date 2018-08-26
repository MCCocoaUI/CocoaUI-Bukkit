package net.mcbbs.cocoaui.util.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import net.mcbbs.cocoaui.CocoaUI;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;

/**
 * @author ChenJi158
 */
public abstract class AbstractConfiguration {

    private static File DataFile = CocoaUI.getKDataFolder();
    private String ConfigName;
    private FileConfiguration config = new KConfiguration();
    private File configFile;

    public FileConfiguration getConfig() {
        return this.config;
    }

    /**
     *
     * @param ConfigName 配置文件的名称
     * @param rewrite 如果文件不存在或读取失败，是否复写。
     * @throws ConfigException 当创建失败时抛出。
     */
    public AbstractConfiguration(String ConfigName, boolean rewrite, String create, String cannotCreate)
            throws ConfigException {
        this.ConfigName = ConfigName;
        this.configFile = new File(DataFile + "/" + ConfigName);

        if (!(AbstractConfiguration.DataFile.exists())) {
            AbstractConfiguration.DataFile.mkdirs();
            Maps.newConcurrentMap();
        }

        if (!(this.configFile.exists())) {
            File file = this.configFile.getParentFile();
            if (file != null && !file.exists()) {
                file.mkdirs();
            }
            try {
                this.configFile.createNewFile();
                if (rewrite) {
                    saveDefaultConfig();
                }
                CocoaUI.getLog().info(create);
            } catch (IOException e) {
                throw new ConfigException(e, cannotCreate);
            }
        }

        try {
            try {
                FileInputStream stream = new FileInputStream(this.configFile);
                InputStreamReader isr = new InputStreamReader(stream, Charsets.UTF_8);
                this.config.load(isr);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (InvalidConfigurationException e) {
            CocoaUI.getLog().warning("错误，无法读取" + ConfigName + ".yml 请注意格式。已经重新生成。");
            this.configFile.renameTo(new File(DataFile + "/" + ConfigName + ".break"));
            if (rewrite) {
                saveDefaultConfig();
            } else {
                try {
                    this.configFile.createNewFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        }

    }

    public abstract void loadConfig();

    public final void saveConfig() throws IOException {
        this.config.save(this.configFile);
    }

    public final File saveFile(File file, InputStreamReader isr) throws ConfigException {
        OutputStreamWriter out = null;
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(file);
            out = new OutputStreamWriter(fileOut, Charsets.UTF_8);
            char buffer[] = new char[4 * 1024];
            int len = 0;
            while ((len = isr.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (IOException e) {
            throw new ConfigException(e, "无法保存默认的配置文件：" + ConfigName);
        } finally {
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    public void saveDefaultConfig() throws ConfigException {
        InputStream i = null;
        try {
            URL url = getClass().getResource("/" + this.ConfigName);
            URLConnection connection;
            connection = url.openConnection();
            connection.setUseCaches(false);
            i = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(i, Charsets.UTF_8);
            saveFile(this.configFile, isr);

            this.saveConfig();

        } catch (IOException ex) {
            throw new ConfigException(ex, "无法保存默认的配置文件：" + ConfigName);
        } finally {
            try {
                if (i != null) {
                    i.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
