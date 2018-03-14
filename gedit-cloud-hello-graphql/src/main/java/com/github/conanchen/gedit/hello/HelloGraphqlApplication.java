package com.github.conanchen.gedit.hello;


import com.github.conanchen.gedit.hello.controller.HelloGrpcClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class HelloGraphqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloGraphqlApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public HelloGrpcClient helloGrpcClient() {
        return new HelloGrpcClient();
    }
}
