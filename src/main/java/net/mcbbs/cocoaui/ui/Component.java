/*
 * Copyright (C) 2018 Bryan_lzh
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.mcbbs.cocoaui.ui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-8-20
 */
public abstract class Component {

    private static int LastComponentUID = 0;

    protected String Name;
    protected int X, Y, Width, Length;
    protected int UID;
    protected List<Component> Child;
    protected boolean Visible = false;

    public Component() {
        this.UID = ++LastComponentUID;
    }

    /**
     * 返回对应的json对象<p>
     * 注意 不需要写入X Y Weigth Length Child Visible等属性
     *
     * @return 对应的json对象
     */
    protected abstract JsonObject toJson();

    public JsonObject toFullJson() {
        JsonObject json = toJson();
        json.addProperty("X", this.X);
        json.addProperty("Y", this.Y);
        json.addProperty("Width", this.Width);
        json.addProperty("Length", this.Length);
        json.addProperty("UID", this.UID);
        json.addProperty("Visible", this.Visible);
        if (Child != null && this.hasChild() && ComponentManager.getComponentRegister(this.getName()).hasChild()) {
            JsonArray arr = new JsonArray();
            for (Component c : Child) {
                arr.add(c.toFullJson());
            }
            json.add("Child", arr);
        }
        return json;
    }

    /**
     * 设置关键属性值<p>
     * <strong>注 本方法用于兼容组件未注册的情况</strong>
     *
     * @param key
     * @param value
     */
    public abstract void setElement(String key, JsonElement value);

    /**
     * 获取关键属性值<p>
     * <strong>注 本方法用于兼容组件未注册的情况</strong>
     *
     * @param <T>
     * @param key
     * @return
     */
    public abstract <T extends JsonElement> T getElement(String key);

    public boolean hasChild() {
        return this.Child != null;
    }

    public int getX() {
        return X;
    }

    public void setX(int X) {
        this.X = X;
    }

    public int getY() {
        return Y;
    }

    public void setY(int Y) {
        this.Y = Y;
    }

    public int getWidth() {
        return Width;
    }

    public void setWidth(int Width) {
        this.Width = Width;
    }

    public int getLength() {
        return Length;
    }

    public void setLength(int Length) {
        this.Length = Length;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public List<Component> getChild() {
        return Child;
    }

    public void setChild(List<Component> Child) {
        this.Child = Child;
    }

    public int getUID() {
        return UID;
    }

    void setUID(int UID) {
        this.UID = UID;
    }

    public boolean isVisible() {
        return Visible;
    }

    public void setVisible(boolean Visible) {
        this.Visible = Visible;
    }

}
