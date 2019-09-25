package com.bianquan.springShop.dao.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bianquan.springShop.entity.shop.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {

    /**
     * 通过上级ID递归获取关联下级所有分类子集
     * @param pid
     * @return
     */
    List<CategoryEntity> listWithChildren(Long pid);

    List<CategoryEntity> fetchPageWithImage(int currIndex, int pageSize, int level, String title);
}
