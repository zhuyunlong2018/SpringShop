package com.bianquan.springShop.modules.admin.serivice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bianquan.springShop.modules.admin.entity.AdminEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AdminService extends IService<AdminEntity> {

    /**
     * 管理员登录
     * @param username
     * @param password
     * @return
     */
    Map<String, Object> login(String username, String password);

    /**
     * 获取管理员的权限列表
     * @param id
     * @return
     */
    Set<String> getUserPermissions(Integer id);

    /**
     * 通过名称检索管理员
     * @param name
     * @return
     */
    AdminEntity queryByName(String name);

    /**
     * 检索携带角色信息、分页处理
     * @param currentPage
     * @param pageSize
     * @return
     */
    Map<String, Object> queryPageWithRole(int currentPage, int pageSize);

    /**
     * 添加管理员信息
     * @param adminEntity
     * @return
     */
    Boolean addAdmin(AdminEntity adminEntity);

    /**
     * 更新管理员
     * @param adminEntity
     * @return
     */
    Boolean editAdmin(AdminEntity adminEntity);

    /**
     * 删除管理员
     * @param id
     * @return
     */
    Boolean deleteAdmin(int id);
}
