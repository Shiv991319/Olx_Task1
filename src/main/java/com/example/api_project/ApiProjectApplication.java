package com.example.api_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ApiProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiProjectApplication.class, args);
    }
}