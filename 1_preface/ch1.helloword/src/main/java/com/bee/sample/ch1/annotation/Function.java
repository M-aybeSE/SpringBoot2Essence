package com.bee.sample.ch1.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author 14530
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Function {
	public String value() default "";
}