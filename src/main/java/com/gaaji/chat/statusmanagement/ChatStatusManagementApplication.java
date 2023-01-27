package com.gaaji.chat.statusmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ChatStatusManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatStatusManagementApplication.class, args);
    }

}
