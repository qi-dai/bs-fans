/**
 * 
 */
package com.eden.fans.bs.domain.annotation;

import java.lang.annotation.*;

/**
 * 默认值注解
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DefalutValue {
	String value();
}
