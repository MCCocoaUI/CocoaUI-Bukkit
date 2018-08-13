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
package net.mcbbs.cocoaui.ui.data;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-8-13
 */
public class Attributes {

    private int X;
    private int Y;
    private int Height;
    private int Width;

    /**
     * 返回绝对坐标X
     * @return 绝对坐标X
     */
    public int getAbsoluteX() {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * 返回绝对坐标Y
     * @return 绝对坐标Y
     */
    public int getAbsoluteY() {
        //TODO
        throw new UnsupportedOperationException();
    }

    /**
     * 返回相对坐标
     * @return 相对坐标X
     */
    public int getX() {
        return X;
    }

    /**
     * 返回相对坐标Y
     * @return 相对坐标Y
     */
    public int getY() {
        return Y;
    }

    /**
     * 返回组件高度
     * @return 组件高度
     */
    public int getHeight() {
        return Height;
    }

    /**
     * 返回组件宽度
     * @return 组件宽度
     */
    public int getWidth() {
        return Width;
    }
    
    

}
