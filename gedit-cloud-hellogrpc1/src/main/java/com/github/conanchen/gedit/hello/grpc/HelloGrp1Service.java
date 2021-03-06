package com.github.conanchen.gedit.hello.grpc;

import com.github.conanchen.gedit.common.grpc.Status;
import com.github.conanchen.gedit.hello.client.HelloGrpc2Client;
import com.google.gson.Gson;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@GRpcService(interceptors = {LogInterceptor.class})
public class HelloGrp1Service extends HelloGrpc.HelloImplBase {
    private static final Logger log = LoggerFactory.getLogger(HelloGrp1Service.class);
    private static final Gson gson = new Gson();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private HelloGrpc2Client helloGrpc2Client;
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        helloGrpc2Client.sayAsyncHello(request.getName(), new HelloGrpc2Client.HelloCallback() {
            @Override
            public void onHelloReply(HelloReply helloReply) {
                responseObserver.onNext(HelloReply.newBuilder(helloReply)
                        .setMessage("hello grpc1," + helloReply.getMessage())
                        .build());
                responseObserver.onCompleted();
                log.info("hello grpc1 invoked");
            }
        });
    }

    @Override
    public void listOldHello(ListHelloRequest request, StreamObserver<HelloReply> responseObserver) {
        for (int i = 0; i < request.getSize(); i++) {
            String uuid = UUID.randomUUID().toString();
            final HelloReply.Builder replyBuilder = HelloReply.newBuilder()
                    .setStatus(Status.newBuilder()
                            .setCode(Status.Code.OK)
                            .setDetails("Hello很高兴回复你，你的hello很温暖@" + DateFormat.getDateTimeInstance().format(new Date()))
                            .build())
                    .setUuid(uuid)
                    .setMessage(String.format("Hello world%s@%s ", uuid, DateFormat.getDateTimeInstance().format(new Date())))
                    .setCreated(System.currentTimeMillis())
                    .setLastUpdated(System.currentTimeMillis());
            HelloReply helloReply = replyBuilder.build();
            responseObserver.onNext(helloReply);
            try {
                Thread.sleep(300);
                log.info("sleep{} to send {}th hello", 300, i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        responseObserver.onCompleted();
    }
}