package net.mcbbs.cocoaui.utils.reflect;

import java.lang.reflect.Constructor;

/**
 * @author Zoyn
 * @since 2018/8/7
 */
@FunctionalInterface
public interface ConstructorFilter {

    boolean accept(@SuppressWarnings("rawtypes") Constructor constructor);

}
