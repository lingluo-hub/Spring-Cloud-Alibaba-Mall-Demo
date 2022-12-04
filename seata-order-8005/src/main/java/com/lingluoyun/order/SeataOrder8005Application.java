package com.lingluoyun.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class SeataOrder8005Application {

    public static void main(String[] args) {
        SpringApplication.run(SeataOrder8005Application.class, args);
    }

}
