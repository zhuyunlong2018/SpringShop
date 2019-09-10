package com.bianquan.springShop.service.admin;

import com.bianquan.springShop.common.exception.RRException;
import com.bianquan.springShop.common.utils.RedisUtil;
import com.bianquan.springShop.dao.admin.MenuDao;
import com.bianquan.springShop.entity.admin.MenuEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 后台权限管理
 */
@Service
public class Permissions {

    private static final Logger logger = LoggerFactory.getLogger(Permissions.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 用户权限redis存储key
     * @param adminId
     * @return
     */
    private static String permissionKey(Integer adminId) {
        return "permission_key_for_" + adminId;
    }

    /**
     * 获取后台登录管理员的访问权限
     * @param id
     * @return
     */
    public Set<String> getUserPermissions(Integer id) {
        Set<Object> permsSet = redisUtil.sGet(permissionKey(id));
        if (permsSet.isEmpty()) {
            //获取用户权限列表
            List<String> permsList = roleService.getPermissionByAdminId(id);
            System.out.println(permsList);
            //通过权限列表获取菜单绑定的shiro权限代号
            List<MenuEntity> menuList = menuDao.getHadCodeByKeys(permsList);
            System.out.println(menuList);
            permsSet = new HashSet<>();
            for(MenuEntity menuEntity : menuList){
                if(StringUtils.isBlank(menuEntity.getCode())){
                    continue;
                }
                permsSet.add(menuEntity.getCode());
                //存储到redis中
                redisUtil.sSet(permissionKey(id), menuEntity.getCode());
            }
        }
        Object obj = permsSet;
        Set<String> permissions = (Set)obj;
        return permissions;
    }

    /**
     * 移除管理员缓存的权限信息
     */
    public void removePermissionsCache() {
        //TODO 如果修改了权限信息，相应的需要清除缓存，考虑编辑情况较少，可以暴力清除所有管理员权限缓存
    }

    /**
     * 获取管理员前端的菜单路由集合
     * @param adminId
     * @return
     */
    public List<MenuEntity> getUserRoutes(Integer adminId) {
        //获取管理员关联角色的前端页面权限
        List<String> permission = roleService.getPermissionByAdminId(adminId);
        if (permission.isEmpty()) {
            throw new RRException("您没有相关权限");
        }
        List<MenuEntity> routes = menuDao.getRoutes(permission);
        return routes;
    }
}
