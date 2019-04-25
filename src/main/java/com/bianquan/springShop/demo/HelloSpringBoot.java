package com.bianquan.springShop.demo;

import com.bianquan.springShop.common.utils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("测试用HELLO")
@RestController
public class HelloSpringBoot {
    @GetMapping("/helloSpringBoot")
    public R HelloSpring (){
        System.out.println("hello spring boot");
        return R.ok("haole");
    }
}