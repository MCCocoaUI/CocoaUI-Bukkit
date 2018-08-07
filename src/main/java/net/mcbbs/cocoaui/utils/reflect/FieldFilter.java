package net.mcbbs.cocoaui.utils.reflect;

import java.lang.reflect.Field;

/**
 * @author Zoyn
 * @since 2018/8/7
 */
@FunctionalInterface
public interface FieldFilter {

    boolean accept(Field field);

}
