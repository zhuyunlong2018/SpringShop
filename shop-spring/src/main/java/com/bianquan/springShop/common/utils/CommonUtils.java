package com.bianquan.springShop.common.utils;

import java.util.Random;

/**
 * 公共工具类
 */
public class CommonUtils {

    /**
     * 生成指定长度的随机字符串
     * @param len
     * @return
     */
    public static String randomStr(Integer len) {
        String strPol = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<len;i++){
            int number=random.nextInt(62);
            sb.append(strPol.charAt(number));
        }
        return sb.toString();
    }
}
