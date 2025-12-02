package com.debanjan.Spring_JPA.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class ModelWrapperConfig {
    @Bean
    public ModelMapper getModelWrapper(){
        return new ModelMapper();
    }
}
