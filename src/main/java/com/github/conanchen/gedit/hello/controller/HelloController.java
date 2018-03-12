package com.github.conanchen.gedit.hello.controller;

import com.github.conanchen.gedit.hello.graphql.mongo.Customer;
import com.github.conanchen.gedit.hello.graphql.mongo.CustomerRepository;
import com.github.conanchen.gedit.hello.grpc.HelloReply;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@RestController
@EnableAutoConfiguration
public class HelloController {
    private final static Gson gson = new Gson();
    //
//    @Autowired
//    private WordRepository wordRepository;
    @Value("${gedit.docker.enabled}")
    Boolean insideDocker = false;


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private HelloGrpcClient helloGrpcClient;


    @RequestMapping(value = "/hello")
    public String hello() {
        String customerFirstNames = getCustomerFirstNames();
        return
                String.format("hello@%s , customerFirstNames=%s HelloController Spring Boot insideDocker=%b",
                        DateFormat.getInstance().format(new Date()), customerFirstNames, insideDocker);
    }

    @RequestMapping(value = "/hellogrpc")
    public ResponseBodyEmitter helloGrpc() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        helloGrpcClient.sayHello("ResponseBodyEmitter-hellogrpc", new HelloGrpcClient.HelloCallback() {
            @Override
            public void onHelloReply(HelloReply helloReply) {
                try {
                    emitter.send(helloReply.getMessage());
                    emitter.complete();
                } catch (IOException e) {
                    e.printStackTrace();
                    emitter.completeWithError(e);
                }
            }
        });
        return emitter;
    }


    private String getCustomerFirstNames() {
        String result = "";
        customerRepository.deleteAll();

        // save a couple of customers
        customerRepository.save(new Customer("Alice", "Smith"));
        customerRepository.save(new Customer("Bob", "Smith"));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : customerRepository.findAll()) {
            result += customer.firstName + ",";
        }
        System.out.println();
        return result;
    }
}