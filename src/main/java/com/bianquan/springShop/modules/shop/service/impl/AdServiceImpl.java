package com.bianquan.springShop.modules.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bianquan.springShop.modules.shop.dao.AdDao;
import com.bianquan.springShop.modules.shop.entity.AdEntity;
import com.bianquan.springShop.modules.shop.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdServiceImpl extends ServiceImpl<AdDao, AdEntity> implements AdService {

    @Autowired
    private AdDao adDao;

}
