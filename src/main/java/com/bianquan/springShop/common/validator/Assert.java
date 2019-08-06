package com.bianquan.springShop.common.validator;

import com.bianquan.springShop.common.exception.RRException;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 */
public abstract class Assert {

    //字符串为空
    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RRException(message);
        }
    }

    //数字为零
    public static void isBlank(long num, String message) {
        if (num == 0) {
            throw new RRException(message);
        }
    }

    //对象为null
    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RRException(message);
        }
    }

    //非正整数
    public static void nonPositiveInteger(Integer num, String message) {
        if (num <=0 ) {
            throw new RRException(message);
        }
    }
}
