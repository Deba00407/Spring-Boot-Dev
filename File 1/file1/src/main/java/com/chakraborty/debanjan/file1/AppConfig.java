package com.chakraborty.debanjan.file1;

import com.chakraborty.debanjan.file1.models.UserModel;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    UserModel userModel() {
        return new UserModel();
    }

    @PostConstruct
    public void init() {
        System.out.println("User model bean is getting initialized....");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("User model bean is getting destroyed....");
    }
}
