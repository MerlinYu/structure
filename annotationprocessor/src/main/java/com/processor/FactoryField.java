package com.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yuchao on 5/23/16.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)

public @interface FactoryField {
  String value() default "";
}
