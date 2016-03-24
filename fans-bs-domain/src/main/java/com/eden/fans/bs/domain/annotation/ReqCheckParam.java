package com.eden.fans.bs.domain.annotation;

import java.lang.annotation.*;

/**
 * Created by lirong5 on 2016/3/24.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReqCheckParam {
    boolean value() default true;
}
