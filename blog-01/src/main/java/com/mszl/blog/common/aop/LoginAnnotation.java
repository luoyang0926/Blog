package com.mszl.blog.common.aop;

import java.lang.annotation.*;

/**
 * 日志注解
 */
//type 代表可以放在类上面，Method 代表可以放在方法上面
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginAnnotation {
    String module() default "";

    String operation() default "";
}
