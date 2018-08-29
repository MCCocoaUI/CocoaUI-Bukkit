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
import net.mcbbs.cocoaui.resource.Resource;
import net.mcbbs.cocoaui.ui.ContainerComponent;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-8-26
 */
public class PanelComponent extends ContainerComponent {

    private Resource Resource;
    
    public PanelComponent(){
        super.Type = "Panel";
    }

    public PanelComponent(Resource Resource) {
        this();
        this.Resource = Resource;
    }
    
    

    @Override
    protected JsonObject toJson() {
        JsonObject json = new JsonObject();
        if (this.Resource != null) {
   //         json.add("Resource", this.Resource.get);
        }
        return json;
    }

    public Resource getResource() {
        return Resource;
    }

    public void setResource(Resource Resource) {
        this.Resource = Resource;
    }

    @Override
    public void setElement(String key, JsonElement value) {
        if("Resource".equals(key)){
            
        }
        super.setElement(key, value);
    }

    @Override
    public <T extends JsonElement> T getElement(String key) {
        
       
        return super.getElement(key);
    }

}
