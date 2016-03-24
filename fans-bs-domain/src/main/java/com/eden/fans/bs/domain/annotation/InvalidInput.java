/**
 * 
 */
package com.eden.fans.bs.domain.annotation;

import java.lang.annotation.*;

/**
 * 无效输入异常
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface InvalidInput {
	String excode();

	String exmsg();
}
