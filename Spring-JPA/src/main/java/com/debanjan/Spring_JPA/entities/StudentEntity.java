package com.debanjan.Spring_JPA.entities;

import com.debanjan.Spring_JPA.utils.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
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
    public String publicId; // safe external id

    @PrePersist
    public void generatePublicId() {
        if(this.publicId == null){
            this.publicId = UUID.randomUUID().toString();
        }
    }

    @NotNull
    @NotBlank
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @Positive(message = "Age cannot be negative or zero")
    private int age;

    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.RATHER_NOT_SAY;

    private String standard;
}
