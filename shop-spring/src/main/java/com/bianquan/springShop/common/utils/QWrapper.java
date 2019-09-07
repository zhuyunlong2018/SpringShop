package com.bianquan.springShop.common.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

public class QWrapper<T> extends QueryWrapper<T> {

    /**
     * 重写mybatis plus的字段转换
     * @param column
     * @return
     */
    @Override
    protected String columnToString(String column) {
        // 驼峰命名转换为下划线命名
        return StringUtils.camelToUnderline(column);
    }
}
