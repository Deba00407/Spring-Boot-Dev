package com.debanjan.Spring_JPA.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "students")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Column(unique = true, nullable = false, updatable = false)
//    @GeneratedValue(generator = "uuid")
//    public String public_id;

    private String name;
    private int age;
    private String gender;
    private String standard;
}
