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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-8-20
 */
public interface ComponentRegister {

    /**
     *
     * @return 组件类型
     */
    String getType();

    /**
     * 创建一个空的组件
     *
     * @return 空的组件
     */
    Component createComponent();

    /**
     * 从json中解析组件数据<p>
     * 注意 不需要解析X Y Weigth Length Child等属性
     *
     * @param g json
     * @return 组件
     */
    Component deJson(JsonObject g);

    default Component deFullJson(JsonObject json) {
        Component com = this.deJson(json);
        com.setX(json.get("X").getAsInt());
        com.setY(json.get("Y").getAsInt());
        com.setWeigth(json.get("Weigth").getAsInt());
        com.setLength(json.get("Length").getAsInt());
        if(this.hasChild() && json.has("Child")){
            JsonArray ja = json.getAsJsonArray("Child");
            com.setChild(ComponentManager.loadChilds(ja));
        }
        return com;
    }

    /**
     *
     * @return 组件的必要参数
     */
    String[] getNecessaryArgs();

    /**
     *
     * @return 组件的可选参数
     */
    String[] getOptionalArgs();

    /**
     *
     * @return 是否有子组件
     */
    boolean hasChild();

    /**
     *
     * @return 控件类
     */
    Class<? extends Component> getComponentClass();
}
