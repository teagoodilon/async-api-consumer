package br.com.compass.pb.asyncapiconsumer;

import br.com.compass.pb.asyncapiconsumer.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@SpringBootTest
class AsyncApiConsumerApplicationTests {

	@Autowired
	private PostService postService;

	@Test
	void testAsync() {

	}
}
