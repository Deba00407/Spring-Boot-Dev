package com.debanjan.Spring_JPA.dto;

import com.debanjan.Spring_JPA.utils.Gender;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO {
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @NotNull(message = "Age is required")
    @Min(value = 5, message = "Age must be at least 5")
    @Max(value = 20, message = "Age cannot be more than 20")
    private Integer age;

    private Gender gender;
    private String standard;
    private Boolean onRoll;
}
