/**
 * 
 */
package com.eden.fans.bs.domain.annotation;

import java.lang.annotation.*;

/**
 * 输入注解
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ActionInput {
	boolean notNull() default true;
}
