package com.thoughtworks.training.gateway1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableFeignClients
@SpringBootApplication
public class Gateway1Application {

	public static void main(String[] args) {
		SpringApplication.run(Gateway1Application.class, args);
	}
}
