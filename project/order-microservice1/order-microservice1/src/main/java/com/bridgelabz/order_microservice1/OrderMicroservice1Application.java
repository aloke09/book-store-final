package com.bridgelabz.order_microservice1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderMicroservice1Application {

	public static void main(String[] args) {
		SpringApplication.run(OrderMicroservice1Application.class, args);
	}

}
