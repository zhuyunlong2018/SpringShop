package com.bianquan.springShop.web.shop.annotation;

import java.lang.annotation.*;

/**
 * 登录校验
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)//注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Documented
public @interface Login {
}
