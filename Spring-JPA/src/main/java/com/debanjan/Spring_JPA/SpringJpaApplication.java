package com.debanjan.Spring_JPA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.TimeZone;

@SpringBootApplication
public class SpringJpaApplication {

	public static void main(String[] args) {

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));

        System.out.println("Loading application");
        SpringApplication.run(SpringJpaApplication.class, args);
	}

}
