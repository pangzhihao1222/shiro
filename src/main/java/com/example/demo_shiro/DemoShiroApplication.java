package com.example.demo_shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.example.demo_shiro.mapper")
@SpringBootApplication
public class DemoShiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoShiroApplication.class, args);
	}

}
