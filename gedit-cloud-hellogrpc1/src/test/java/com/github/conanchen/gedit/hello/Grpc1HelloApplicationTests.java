package com.github.conanchen.gedit.hello;

import com.github.conanchen.gedit.hello.client.HelloGrpc2Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Grpc1HelloApplicationTests {
	@Autowired
	private HelloGrpc2Client helloGrpc2Client;

	@Test
	public void helloClintTest(){
//		helloGrpc2Client.downloadOldHellos();
	}

}
