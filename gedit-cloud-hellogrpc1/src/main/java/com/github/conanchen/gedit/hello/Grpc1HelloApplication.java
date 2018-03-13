package com.github.conanchen.gedit.hello;


import com.github.conanchen.gedit.hello.controller.HelloGrpcClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Grpc1HelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(Grpc1HelloApplication.class, args);
    }

    @Bean
    public HelloGrpcClient helloGrpcClient() {
        return new HelloGrpcClient();
    }
}
