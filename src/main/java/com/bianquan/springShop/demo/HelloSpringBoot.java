package com.bianquan.springShop.demo;

import com.bianquan.springShop.common.utils.Response;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("测试用HELLO")
@RestController
public class HelloSpringBoot {
    @GetMapping("/helloSpringBoot")
    public Response HelloSpring (){
        System.out.println("hello spring boot");
        return Response.ok("haole");
    }
}