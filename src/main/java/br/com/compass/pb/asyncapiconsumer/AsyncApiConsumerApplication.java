package br.com.compass.pb.asyncapiconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AsyncApiConsumerApplication{

	public static void main(String[] args) {
		SpringApplication.run(AsyncApiConsumerApplication.class, args);
	}

}
