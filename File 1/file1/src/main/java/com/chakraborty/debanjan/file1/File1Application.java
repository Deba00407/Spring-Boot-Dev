package com.chakraborty.debanjan.file1;

import com.chakraborty.debanjan.file1.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class File1Application implements CommandLineRunner {
    // run this file only after all beans have been generated

    final private UserModel userModel;

    public File1Application(UserModel userModel) {
        this.userModel = userModel;
    }

	public static void main(String[] args) {
        SpringApplication.run(File1Application.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        userModel.printDetails();
    }
}
