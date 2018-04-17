package com.hotent.core.annotion.query;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Field {
	String name();

	String desc();

	String dataType() default "varchar";

	String options() default "";

	short controlType() default 1;

	String style() default "";
}