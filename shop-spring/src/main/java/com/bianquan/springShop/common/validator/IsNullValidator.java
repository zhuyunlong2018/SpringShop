package com.bianquan.springShop.common.validator;

import com.bianquan.springShop.common.annotation.IsNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义IsNull实现类，被注解字段前端不能传值过来
 */
public class IsNullValidator implements ConstraintValidator<IsNull, Object> {

    private boolean required;

    // 重写initialize方法获取注解实例
    @Override
    public void initialize(IsNull ca) {
        // 重注解实例中获信息
        required = ca.required();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        // object就是要校验的数据了
        if (object != null) {
            return false;
        }
        return true;
    }
}
