package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Arrays;

@SpringBootApplication
@RestController
@MapperScan("com.dao")
public class PaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
    }

    public PaymentApplication() {

        File file = new File("src/main/resources");

        String[] list = file.list();

        System.out.println(Arrays.toString(list));
    }

    public static void t(String name) {

        System.out.println(name.hashCode());
    }
}