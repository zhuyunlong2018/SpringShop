package com.bianquan.springShop.modules.admin.controller;

import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public abstract class AbstractController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected Gson gson;

    protected Map getUser() {
        String json = SecurityUtils.getSubject().getPrincipal().toString();
        Map map = gson.fromJson(json, Map.class);
        return map;
    }

    protected Integer getUserId() {
        Double id = (Double) getUser().get("id");
        return id.intValue();
    }

    protected String getUserName() {
        return (String) getUser().get("name");
    }

}
