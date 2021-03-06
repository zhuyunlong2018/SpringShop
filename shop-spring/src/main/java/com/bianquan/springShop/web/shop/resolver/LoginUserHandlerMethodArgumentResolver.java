package com.bianquan.springShop.web.shop.resolver;

import com.bianquan.springShop.web.shop.annotation.LoginUser;
import com.bianquan.springShop.entity.shop.UserEntity;
import com.bianquan.springShop.web.shop.interceptor.AuthorizationInterceptor;
import com.bianquan.springShop.service.shop.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return (
                parameter.getParameterType().isAssignableFrom(UserEntity.class) ||
                        parameter.getParameterType().isAssignableFrom(Long.class)
        ) &&
                parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        //获取用户ID
        Object object = request.getAttribute(AuthorizationInterceptor.USER_KEY, RequestAttributes.SCOPE_REQUEST);

        if (object == null) {
            return null;
        }

        //如果为Long类型，返回用户ID
        if (parameter.getParameterType().isAssignableFrom(Long.class)) {
            return object;
        }

        //否者返回用户实体
        UserEntity user = userService.getById((Long) object);
        return user;
    }
}
