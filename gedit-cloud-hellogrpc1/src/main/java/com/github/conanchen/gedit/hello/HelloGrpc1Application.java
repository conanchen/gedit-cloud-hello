package com.github.conanchen.gedit.hello;


import com.github.conanchen.gedit.hello.controller.HelloGrpcClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HelloGrpc1Application {

    public static void main(String[] args) {
        SpringApplication.run(HelloGrpc1Application.class, args);
    }

    @Bean
    public HelloGrpcClient helloGrpcClient() {
        return new HelloGrpcClient();
    }
}
