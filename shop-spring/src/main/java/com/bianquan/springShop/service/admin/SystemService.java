package com.bianquan.springShop.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemService {

    @Autowired
    private Permissions permissions;

    /**
     * 移除系统的缓存
     */
    public void refreshCache() {
        //管理员权限缓存
        permissions.removePermissionsCache();
    }
}
