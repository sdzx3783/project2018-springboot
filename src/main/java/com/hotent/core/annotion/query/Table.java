package com.hotent.core.annotion.query;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Table {
	String name() default "";

	String var() default "";

	String displayTagId() default "";

	String pk() default "ID";

	String comment() default "";

	boolean isPrimary() default true;

	String relation() default "";

	String primaryTable() default "";
}