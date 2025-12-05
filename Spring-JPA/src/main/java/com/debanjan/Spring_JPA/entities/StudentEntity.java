package com.debanjan.Spring_JPA.entities;

import com.debanjan.Spring_JPA.custom_annotations.OddNumber;
import com.debanjan.Spring_JPA.utils.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "students")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, updatable = false, nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String publicId; // safe external id

    @PrePersist
    public void generatePublicId() {
        if(this.publicId == null){
            this.publicId = UUID.randomUUID().toString();
        }

        if(this.onRoll == null){
            this.onRoll = true;
        }
    }

    @NotNull
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @Positive(message = "Age cannot be negative or zero")
    @Min(value = 5, message = "Age must be at least 5 year old")
    @Max(value = 20, message = "Age cannot be more than 20 years old")
    private int age;

    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.RATHER_NOT_SAY;

    private String standard;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @Column(nullable = false)
    private Boolean onRoll = true; // default value unless otherwise specified
}
