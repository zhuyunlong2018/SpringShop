package com.bianquan.springShop.common.annotation;


import com.bianquan.springShop.common.validator.IsNullValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsNullValidator.class)
public @interface IsNull {
    boolean required() default true;
    String message() default "传递多余字段";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
