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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-8-22
 */
public class UnknowComponet extends Component {

    private Map<String, Object> Values = new HashMap<>();

    public UnknowComponet() {
    }
    
    

    public UnknowComponet(JsonObject json) {
        super.Name = json.get("Name").getAsString();
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        for (Map.Entry<String, Object> e : Values.entrySet()) {
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
    public void set(String key, Object value) {
        Values.put(key, value);
    }

    @Override
    public <T> T get(String key) {
        return (T) Values.get(key);
    }
    
    
    
    public static final class UnknowComponetRegister implements ComponentRegister{

        @Override
        public String getType() {
            return "Unknow";
        }

        @Override
        public Component createComponent() {
            return new UnknowComponet();
        }

        @Override
        public Component deJson(JsonObject g) {
            return new UnknowComponet(g);
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
            return UnknowComponet.class;
        }
        
    }

}