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
package net.mcbbs.cocoaui.ui.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.mcbbs.cocoaui.ui.Component;
import net.mcbbs.cocoaui.ui.ComponentRegister;
import net.mcbbs.cocoaui.ui.ContainerComponent;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-8-22
 */
public class UnknownComponent extends ContainerComponent {

    private Map<String, JsonElement> Values = new HashMap<>();
    private static Set<String> KEEP_KEY = new HashSet<>(Arrays.asList("Name", "X", "Y", "Width", "Length", "UID", "Visible", "Child")); 

    public UnknownComponent(JsonObject json) {
        super.Type = json.get("Type").getAsString();
        for (Map.Entry<String, JsonElement> e : json.entrySet()) {
            if (!KEEP_KEY.contains(e.getKey())) {
                Values.put(e.getKey(), e.getValue());
            }
        }
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        for (Map.Entry<String, JsonElement> e : Values.entrySet()) {
            Object value = e.getValue();
            if (value instanceof JsonElement) {
                json.add(e.getKey(), (JsonElement) value);
            }
            JsonElement v = null;
            if (value instanceof String) {
                v = new JsonPrimitive((String) value);
            } else if (value instanceof Number) {
                v = new JsonPrimitive((Number) value);
            } else if (value instanceof Character) {
                v = new JsonPrimitive((Character) value);
            } else if (value instanceof Boolean) {
                v = new JsonPrimitive((Boolean) value);
            }
            if (v != null) {
                json.add(e.getKey(), v);
            }
        }
        return json;
    }

    @Override
    public void setElement(String key, JsonElement value) {
        Values.put(key, value);
    }

    @SuppressWarnings("unchecked")
	@Override
    public <T extends JsonElement> T getElement(String key) {
        return (T) Values.get(key);
    }

    public static final class UnknownComponentRegister implements ComponentRegister {

        @Override
        public String getType() {
            return "Unknown";
        }

        @Override
        public Component createComponent() {
            throw new UnsupportedOperationException("该组件不支持手动创建");
        }

        @Override
        public Component deJson(JsonObject g) {
            return new UnknownComponent(g);
        }

        @Override
        public String[] getNecessaryArgs() {
            return new String[]{};
        }

        @Override
        public String[] getOptionalArgs() {
            return new String[]{};
        }

        @Override
        public boolean hasChild() {
            return true;
        }

        @Override
        public Class<? extends Component> getComponentClass() {
            return UnknownComponent.class;
        }

    }

}
