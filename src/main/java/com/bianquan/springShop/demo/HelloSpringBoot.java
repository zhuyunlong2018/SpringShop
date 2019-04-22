package com.bianquan.springShop.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloSpringBoot {
    @RequestMapping(path = {"/helloSpringBoot"})
    public String HelloSpring (){
        System.out.println("hello spring boot");
        return "hello spring boot";
    }
}