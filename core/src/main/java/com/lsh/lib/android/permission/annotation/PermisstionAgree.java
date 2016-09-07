package com.lsh.lib.android.permission.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Author:liush
 * Date:2016/8/24
 */
@Target(ElementType.METHOD)
public @interface PermisstionAgree {
    int value() default -1;
}
