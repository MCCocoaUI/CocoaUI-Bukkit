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
import net.mcbbs.cocoaui.ui.Component;
import net.mcbbs.cocoaui.ui.ComponentRegister;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-8-25
 */
public class LabelComponent extends Component {

    private JsonElement Text;

    public LabelComponent() {
        super.Type = "Label";
    }

    public LabelComponent(String text) {
        this();
        this.Text = new JsonPrimitive(text);
    }

    public LabelComponent(JsonElement Text) {
        this();
        this.Text = Text;
    }

    @Override
    protected JsonObject toJson() {
        JsonObject json = new JsonObject();
        if (this.Text != null) {
            json.add("Text", this.Text);
        }
        return json;
    }

    @Override
    public void setElement(String key, JsonElement value) {
        if ("Text".equalsIgnoreCase(key)) {
            if (!value.isJsonPrimitive()) {
                throw new IllegalArgumentException("Text属性必须为JsonPrimitive");
            }
            if (!value.getAsJsonPrimitive().isString()) {
                throw new IllegalArgumentException("Text属性必须为String");
            }
            this.Text = value;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends JsonElement> T getElement(String key) {
        if ("Text".equalsIgnoreCase(key)) {
            return (T) this.Text;
        }
        return null;
    }

    public void setTest(String s) {
        this.Text = new JsonPrimitive(s);
    }

    public String getText() {
        return this.Text == null ? null : this.Text.getAsString();
    }

    @Override
    public boolean hasChild() {
        return false;
    }

    public static class LabelComponentRegister implements ComponentRegister {

        @Override
        public String getType() {
            return "Label";
        }

        @Override
        public LabelComponent createComponent() {
            return new LabelComponent();
        }

        @Override
        public Component deJson(JsonObject g) {
            LabelComponent com = this.createComponent();
            if (g.has("Text")) {
                com.setTest(g.get("Text").getAsString());
            }
            return com;
        }

        @Override
        public String[] getNecessaryArgs() {
            return new String[]{"Text"};
        }

        @Override
        public String[] getOptionalArgs() {
            return new String[]{};
        }

        @Override
        public boolean hasChild() {
            return false;
        }

        @Override
        public Class<? extends Component> getComponentClass() {
            return LabelComponent.class;
        }

    }

}
