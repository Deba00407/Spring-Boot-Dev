package com.debanjan.Spring_JPA.dto;

import com.debanjan.Spring_JPA.utils.Logger;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO {
    private String name;
    private int age;
    private String gender;
    private String standard;

    public void logDetails() {
        Logger.logEntity(this);
    }
}
