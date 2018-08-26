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

import net.mcbbs.cocoaui.ui.component.LabelComponent;
import net.mcbbs.cocoaui.ui.component.UnknownComponent;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-8-21
 */
public class ComponentManager {

    private static final Map<String, ComponentRegister> COMPONENT_REGISTERS = new HashMap<>();
    private static final Map<Class<? extends Component>, String> COMPONENT_CLASSES = new HashMap<>();
    private static final ComponentRegister UNKNOWN_COMPONENT_REGISTER = new UnknownComponent.UnknownComponentRegister();

    static {
        registerComponentRegister(UNKNOWN_COMPONENT_REGISTER);
        registerComponentRegister(new LabelComponent.LabelComponentRegister());
    }

    public static void registerComponentRegister(ComponentRegister cr) {
        COMPONENT_REGISTERS.put(cr.getType(), cr);
        COMPONENT_CLASSES.put(cr.getComponentClass(), cr.getType());
    }

    public static ComponentRegister getComponentRegister(Class<? extends Component> c) {
        String s = COMPONENT_CLASSES.get(c);
        return COMPONENT_REGISTERS.get(s);
    }

    public static List<Component> loadChilds(JsonArray json) {
        List<Component> list = new ArrayList<>();
        for (JsonElement jsonElement : json) {
            list.add(loadComponent(jsonElement));
        }
        return list;
    }

    public static Component loadComponent(JsonElement json) {
        JsonObject obj = json.getAsJsonObject();
        String name = obj.get("Name").getAsString();
        ComponentRegister cr = getComponentRegister(name);
        return cr.deFullJson(obj);
    }

    public static ComponentRegister getComponentRegister(String name) {
        ComponentRegister cr = COMPONENT_REGISTERS.get(name);
        return cr == null ? UNKNOWN_COMPONENT_REGISTER : cr;
    }

}
