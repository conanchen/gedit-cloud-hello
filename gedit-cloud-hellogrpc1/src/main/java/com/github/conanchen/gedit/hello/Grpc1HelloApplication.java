package com.github.conanchen.gedit.hello;


import com.github.conanchen.gedit.hello.client.HelloGrpc2Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Grpc1HelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(Grpc1HelloApplication.class, args);
    }
}
