package com.bianquan.springShop.service.shop.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bianquan.springShop.dao.shop.AdDao;
import com.bianquan.springShop.entity.shop.AdEntity;
import com.bianquan.springShop.service.shop.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdServiceImpl extends ServiceImpl<AdDao, AdEntity> implements AdService {

    @Autowired
    private AdDao adDao;

}
