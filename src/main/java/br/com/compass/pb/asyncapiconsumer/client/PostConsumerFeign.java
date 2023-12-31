package br.com.compass.pb.asyncapiconsumer.client;

import br.com.compass.pb.asyncapiconsumer.entity.Comment;
import br.com.compass.pb.asyncapiconsumer.entity.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "post-consumer", url = "https://jsonplaceholder.typicode.com/posts")
public interface PostConsumerFeign {

    @GetMapping(value = "/{id}")
    Post getPostById(@PathVariable("id") Long id);

    @GetMapping(value = "/{id}/comments")
    List<Comment> getCommentsById(@PathVariable("id") Long id);
}
