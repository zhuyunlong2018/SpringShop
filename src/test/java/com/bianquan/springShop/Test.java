package com.bianquan.springShop;

import com.bianquan.springShop.common.utils.ObjectTranscoder;
import com.bianquan.springShop.modules.admin.entity.AdminEntity;

import java.io.UnsupportedEncodingException;

public class Test {

    @org.junit.Test
    public void test() throws UnsupportedEncodingException {
        AdminEntity admin = new AdminEntity();
        admin.setName("test admin");

        byte[] serialize = ObjectTranscoder.serialize(admin);

        String ser = new String(serialize, "ISO-8859-1");


        byte[] by = ser.getBytes("ISO-8859-1");
        System.out.println(by==serialize);
        System.out.println(ser.equals(by.toString()));
        System.out.println(by);

        AdminEntity newAdmin = (AdminEntity) ObjectTranscoder.deserialize(by);
        System.out.println(newAdmin);
    }
}
