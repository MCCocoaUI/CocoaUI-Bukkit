package net.mcbbs.cocoaui.utils.reflect;

import java.lang.reflect.Method;

/**
 * @author Zoyn
 * @since 2018/8/7
 */
@FunctionalInterface
public interface MethodFilter {

    boolean accept(Method method);

}
