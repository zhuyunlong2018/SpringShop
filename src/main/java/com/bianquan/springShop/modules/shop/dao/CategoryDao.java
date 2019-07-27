package com.bianquan.springShop.modules.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bianquan.springShop.modules.shop.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {

    List<CategoryEntity> listWithChildren(Long pid);
}
