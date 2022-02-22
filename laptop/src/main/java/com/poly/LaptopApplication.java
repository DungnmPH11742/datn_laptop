package com.poly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class LaptopApplication {
    public static void main(String[] args) {
        SpringApplication.run(LaptopApplication.class, args);
    }
}
