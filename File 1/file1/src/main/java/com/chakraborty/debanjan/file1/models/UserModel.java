package com.chakraborty.debanjan.file1.models;


import com.chakraborty.debanjan.file1.schemas.user;
import org.springframework.stereotype.Component;

@Component
public class UserModel extends user {
    public UserModel() {
        this.name = "Not initialized";
        this.email = "Not initialized";
        this.age = 0;
    }

    public UserModel(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    @Override
    public void printDetails() {
        System.out.println("Name: " + this.name + "\nEmail: " + this.email + "\nAge: " + this.age);
    }
}
