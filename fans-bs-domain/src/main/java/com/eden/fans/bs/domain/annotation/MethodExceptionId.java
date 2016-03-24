package com.eden.fans.bs.domain.annotation;


import java.lang.annotation.*;

/**
 * @author lirong
 * @email lirong5@jd.com
 * @date 2015年11月06日
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MethodExceptionId {
    String value() default "";
    boolean demoteFlag() default false;
}
