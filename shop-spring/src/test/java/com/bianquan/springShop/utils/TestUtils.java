package com.bianquan.springShop.utils;

import com.bianquan.springShop.common.utils.CommonUtils;
import org.junit.Test;

public class TestUtils {

    @Test
    public void testRandom() {
        String str = CommonUtils.randomStr(12);
        System.out.println(str);
    }
}
