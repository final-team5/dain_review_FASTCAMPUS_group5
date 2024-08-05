package com.example.finalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class Fc8FinalProjectGroup5Application {

    public static void main(String[] args) {
        SpringApplication.run(Fc8FinalProjectGroup5Application.class, args);
    }

}
