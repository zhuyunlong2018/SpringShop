package com.bianquan.springShop;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Test {

    @org.junit.Test
    public void test() throws UnsupportedEncodingException {
        Map map = new HashMap();
        map.put("name", "zhangsan");
        map.put("age", 10.9);

        String json = new Gson().toJson(map);

        System.out.println(json);

        Map<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap = new Gson().fromJson(json, Map.class);
        Double age = (Double) objectObjectHashMap.get("age");
        System.out.println(age.intValue());
    }
}
