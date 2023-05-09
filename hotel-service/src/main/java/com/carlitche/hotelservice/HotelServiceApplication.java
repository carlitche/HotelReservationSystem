package com.carlitche.hotelservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class HotelServiceApplication {

	public static void main(String[] args) {
		var app = SpringApplication.run(HotelServiceApplication.class, args);
//		Arrays.stream(app.getBeanDefinitionNames()).forEach(System.out::println);
	}

}
