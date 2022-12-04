package com.lingluoyun.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class SeataStorage8006Application {

    public static void main(String[] args) {
        SpringApplication.run(SeataStorage8006Application.class, args);
    }

}
