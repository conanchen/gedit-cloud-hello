package com.github.conanchen.gedit.hello.client;

import com.github.conanchen.gedit.common.grpc.Status;
import com.github.conanchen.gedit.hello.grpc.HelloGrpc;
import com.github.conanchen.gedit.hello.grpc.HelloReply;
import com.github.conanchen.gedit.hello.grpc.HelloRequest;
import com.github.conanchen.gedit.hello.grpc.ListHelloRequest;
import com.google.gson.Gson;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.health.v1.HealthCheckRequest;
import io.grpc.health.v1.HealthCheckResponse;
import io.grpc.health.v1.HealthGrpc;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
@Component
public class HelloGrpc2Client {

    private final static String TAG = HelloGrpc2Client.class.getSimpleName();

    @Value("${hellogrpc.port}")
    private int port;

    public interface HelloCallback {
        void onHelloReply(HelloReply helloReply);
    }

    private static final Gson gson = new Gson();

    private static final Logger logger = Logger.getLogger(HelloGrpc2Client.class.getName());
    final HealthCheckRequest healthCheckRequest = HealthCheckRequest
            .newBuilder()
            .setService(HelloGrpc.getServiceDescriptor().getName())
            .build();

    private ManagedChannel getManagedChannel() {
        return NettyChannelBuilder
                .forAddress("127.0.0.1", port)
                .usePlaintext(true)
                //                .keepAliveTime(60, TimeUnit.SECONDS)
                .build();
    }

    public void sayAsyncHello(String name, HelloCallback callback) {
        ManagedChannel channel = getManagedChannel();

        ConnectivityState connectivityState = channel.getState(true);
        System.out.println(String.format("sayAsyncHello connectivityState = [%s]", gson.toJson(connectivityState)));

        HealthGrpc.HealthStub healthStub = HealthGrpc.newStub(channel);
        HelloGrpc.HelloStub helloStub = HelloGrpc.newStub(channel);

        helloStub.withDeadlineAfter(60, TimeUnit.SECONDS)
                .sayHello(HelloRequest.newBuilder().setName(name).build(), new StreamObserver<HelloReply>() {
                    @Override
                    public void onNext(HelloReply helloReply) {

                        System.out.println(String.format("sayAsyncHello got helloReply %s:%s gson=[%s]", helloReply.getUuid(), helloReply.getMessage(), gson.toJson(helloReply)));
                        callback.onHelloReply(helloReply);
                    }

                    @Override
                    public void onError(Throwable t) {
                        logger.info(String.format("helloStub.sayAsyncHello() onError %s", t.getMessage()));
                        callback.onHelloReply(HelloReply.newBuilder().setStatus(Status.newBuilder()
                                .setCode(com.github.conanchen.gedit.common.grpc.Status.Code.UNAVAILABLE)
                                .setDetails("Hello API 错误：可能网络不通或服务器错误")
                                .build()).build());
                    }

                    @Override
                    public void onCompleted() {
                        logger.info(String.format("helloStub.sayAsyncHello() onCompleted"));
                    }
                });

        healthStub.withDeadlineAfter(60, TimeUnit.SECONDS).check(healthCheckRequest,
                new StreamObserver<HealthCheckResponse>() {
                    @Override
                    public void onNext(HealthCheckResponse value) {

                        if (value.getStatus() == HealthCheckResponse.ServingStatus.SERVING) {
                            System.out.println(String.format("sayAsyncHello healthStub.check onNext YES! ServingStatus.SERVING name = [%s]", name));
                        } else {
                            System.out.println(String.format("sayAsyncHello healthStub.check onNext NOT! ServingStatus.SERVING name = [%s]", name));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println(String.format("sayAsyncHello healthStub.check onError grpc service check health\n%s", t.getMessage()));
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println(String.format("sayAsyncHello healthStub.check onCompleted grpc service check health\n%s", ""));
                    }
                });

    }

    public void downloadOldHellos(ListHelloRequest request, HelloCallback callback) {
        ManagedChannel channel = getManagedChannel();
        HelloGrpc.HelloStub helloStub = HelloGrpc.newStub(channel);
        helloStub.withDeadlineAfter(60, TimeUnit.SECONDS)
                .listOldHello(
                        request, new StreamObserver<HelloReply>() {
                            @Override
                            public void onNext(HelloReply value) {
                                callback.onHelloReply(value);
                            }

                            @Override
                            public void onError(Throwable t) {
                                logger.info(String.format("helloStub.listOldHello() onError %s", t.getMessage()));

                            }

                            @Override
                            public void onCompleted() {
                                logger.info(String.format("helloStub.listOldHello() onCompleted"));
                            }
                        }
                );

    }


}
