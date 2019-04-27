package com.bianquan.springShop.common.annotation;

import com.bianquan.springShop.common.validator.IsPhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

//说明该注解将被包含在javadoc中
@Documented
// 注解的作用目标 类上、方法上、属性上
@Target({ElementType.FIELD, ElementType.PARAMETER})
// 注解的保留位置　　
@Retention(RetentionPolicy.RUNTIME)
// 校验注解必须使用@Constraint
@Constraint(validatedBy = IsPhoneValidator.class)
public @interface IsPhone {
    boolean required() default true;
    String message() default "手机号不正确";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}