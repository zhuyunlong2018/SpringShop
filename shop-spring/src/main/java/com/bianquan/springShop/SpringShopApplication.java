package com.bianquan.springShop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = "com.bianquan.springShop")
public class SpringShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringShopApplication.class, args);
	}

}
