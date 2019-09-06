package com.bianquan.springShop.modules.admin.controller;

import com.bianquan.springShop.modules.admin.entity.AdminEntity;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected Integer getUserId() {
        return Integer.parseInt(SecurityUtils.getSubject().getPrincipal().toString());
    }

}
