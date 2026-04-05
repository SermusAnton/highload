package com.highload.dialog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DialogApplication {

    public static void main(String[] args) {
        SpringApplication.run(DialogApplication.class, args);
    }
}
