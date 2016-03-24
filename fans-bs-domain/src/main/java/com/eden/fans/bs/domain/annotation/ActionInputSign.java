/**
 * 
 */
package com.eden.fans.bs.domain.annotation;

import java.lang.annotation.*;

/**
 * 签名组成参数
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ActionInputSign {

}
