package net.mcbbs.cocoaui.utils.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

/**
 * 解决了在1.7或更低版本，中文变U码的问题，同时统一编码UTF8。 用法与YmalConfiguration相同。
 *
 * @author ChenJi158
 *
 */
public class KConfiguration extends YamlConfiguration {

    private DumperOptions options;
    private Representer representer;
    private Yaml yml;

    public static KConfiguration loadFromFile(File file) throws IOException, InvalidConfigurationException {
        FileInputStream stream = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(stream, Charsets.UTF_8);
        KConfiguration config = new KConfiguration();
        config.load(isr);
        return config;
    }

    public KConfiguration() {
        super();
        this.getYmal();
        this.getYmalOptions();
        this.getYmalRepresenter();
    }

    @Override
    public void loadFromString(String s) throws InvalidConfigurationException {
        super.loadFromString(s);
        this.getYmal();
        this.getYmalOptions();
        this.getYmalRepresenter();
    }

    @Override
    public String saveToString() {
        this.options.setIndent(options().indent());
        this.options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        this.options.setAllowUnicode(true);
        this.representer.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        String header = buildHeader();
        String dump = this.yml.dump(getValues(false));
        if (dump.equals("{}\n")) {
            dump = "";
        }
        return header + dump;
    }

    @Override
    public void save(File file) throws IOException {
        if (file == null) {
            return;
        }
        Files.createParentDirs(file);
        String data = this.saveToString();
        FileOutputStream fileout = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(fileout, Charsets.UTF_8);
        try {
            writer.write(data.toCharArray());
        } finally {
            writer.close();
        }
    }

    private void getYmalOptions() {
        Field f;
        try {
            f = this.getField("yamlOptions");
            f.setAccessible(true);
            this.options = (DumperOptions) f.get(this);
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
            e.printStackTrace();
        }
    }

    private void getYmalRepresenter() {
        Field f;
        try {
            f = this.getField("yamlRepresenter");
            f.setAccessible(true);
            this.representer = (Representer) f.get(this);
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
            e.printStackTrace();
        }
    }

    private void getYmal() {
        Field f;
        try {
            f = this.getField("yaml");
            f.setAccessible(true);
            this.yml = (Yaml) f.get(this);
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
            e.printStackTrace();
        }
    }

    public Field getField(String n) {
        for (Field f : YamlConfiguration.class.getDeclaredFields()) {
            if (f.getName().equals(n)) {
                return f;
            }
        }
        return null;
    }

}
