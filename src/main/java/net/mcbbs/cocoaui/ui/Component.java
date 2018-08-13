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

import java.util.ArrayList;
import java.util.List;
import net.mcbbs.cocoaui.ui.data.Attributes;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-8-13
 */
public abstract class Component {
    
    protected Attributes componentAttr;
    
    private List<Component> subComponent;
    
    
    
    public void addComponent(Component cmp){
        if(subComponent == null){
            subComponent = new ArrayList<>();
        }
        subComponent.add(cmp);
    }
    

}
