package com.bianquan.springShop.dao.shop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bianquan.springShop.entity.shop.AdEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdDao extends BaseMapper<AdEntity> {

    /**
     * 通过key值获取banner列表
     * @param key
     * @return
     */
    List<AdEntity> fetchListByKey(String key);
}
