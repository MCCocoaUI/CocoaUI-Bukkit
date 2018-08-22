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

import com.google.gson.JsonObject;
import java.util.List;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-8-20
 */
public abstract class Component {

    protected String Name;
    protected int X, Y, Weigth, Length;
    protected List<Component> Child;

    /**
     * 返回对应的json对象<p>
     * 注意 不需要写入X Y Weigth Length Child等属性
     *
     * @return 对应的json对象
     */
    public abstract JsonObject toJson();

    /**
     * 设置关键属性值<p>
     * 注 本方法用于兼容组件未注册的情况
     *
     * @param key
     * @param value
     */
    public abstract void set(String key, Object value);

    /**
     * 获取关键属性值<p>
     * 注 本方法用于兼容组件未注册的情况
     * @param <T>
     * @param key
     * @return
     */
    public abstract <T> T get(String key);

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

    public int getWeigth() {
        return Weigth;
    }

    public void setWeigth(int Weigth) {
        this.Weigth = Weigth;
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
    

    
}
