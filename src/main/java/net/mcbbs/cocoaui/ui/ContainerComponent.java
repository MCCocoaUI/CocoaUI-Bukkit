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
 * @since 2018-8-26
 */
public abstract class ContainerComponent extends Component {

    protected List<Component> Child;

    @Override
    public JsonObject toFullJson() {
        JsonObject json = super.toFullJson();
        if (Child != null && this.hasChild() && ComponentManager.getComponentRegister(this.getType()).hasChild()) {
            JsonArray arr = new JsonArray();
            for (Component c : Child) {
                arr.add(c.toFullJson());
            }
            json.add("Child", arr);
        }
        return json;
    }

    @Override
    public boolean hasChild() {
        return this.Child != null;
    }

    public List<Component> getChild() {
        return Child;
    }

    public void setChild(List<Component> Child) {
        this.Child = Child;
    }

    @Override
    public void setElement(String key, JsonElement value) {
        if ("Child".equals(key)) {
            if (value instanceof JsonArray) {
                JsonArray js = (JsonArray) value;
                this.Child = ComponentManager.loadChilds(js);
            } else {
                throw new IllegalArgumentException("Child 的json元素必须为JsonArray");
            }
        }
    }

    @Override
    public <T extends JsonElement> T getElement(String key) {
        if ("Child".equals(key)) {
            JsonArray ja = new JsonArray();
            if (this.Child != null) {
                for (Component c : this.Child) {
                    ja.add(c.toFullJson());
                }
            }
            return (T) ja;
        }
        return null;
    }

}
