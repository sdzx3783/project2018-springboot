package com.hotent.core.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldDescription {
	boolean pk() default false;

	String columnName() default "";

	boolean canUpd() default true;
}