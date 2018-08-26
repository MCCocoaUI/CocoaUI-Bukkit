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

    protected String Type;
    protected String X, Y, Width, Length;
    protected int UID;
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
        if(!json.has("Type")){
            json.addProperty("Type", this.getType());
        }
        json.addProperty("X", this.X);
        json.addProperty("Y", this.Y);
        json.addProperty("Width", this.Width);
        json.addProperty("Length", this.Length);
        json.addProperty("UID", this.UID);
        json.addProperty("Visible", this.Visible);
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

    public abstract boolean hasChild();

    public String getX() {
        return X;
    }

    public void setX(String X) {
        this.X = X;
    }

    public String getY() {
        return Y;
    }

    public void setY(String Y) {
        this.Y = Y;
    }

    public String getWidth() {
        return Width;
    }

    public void setWidth(String Width) {
        this.Width = Width;
    }

    public String getLength() {
        return Length;
    }

    public void setLength(String Length) {
        this.Length = Length;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
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
