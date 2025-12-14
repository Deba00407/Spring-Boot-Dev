package com.debanjan.e_commerce;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.util.TimeZone;

@SpringBootApplication
public class ECommerceApplication {

//    private final Environment environment;

//    public ECommerceApplication(Environment environment) {
//        this.environment = environment;
//    }

    public static void main(String[] args) {
        System.out.printf("Spring boot app: %s started%n", ECommerceApplication.class.getSimpleName());
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
        SpringApplication.run(ECommerceApplication.class, args);
    }

//    @PostConstruct
//    public void init() {
//        String timezone = environment.getProperty("user.timezone", "UTC");
//        TimeZone.setDefault(TimeZone.getTimeZone(timezone));
//    }
}

