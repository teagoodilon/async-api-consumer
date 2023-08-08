package br.com.compass.pb.asyncapiconsumer.client;

import br.com.compass.pb.asyncapiconsumer.domain.entity.Comment;
import br.com.compass.pb.asyncapiconsumer.domain.entity.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "feign-consumer", url = "https://jsonplaceholder.typicode.com/comments")
public interface CommentConsumerFeign {

    @GetMapping(value = "")
    List<Comment> getComments();

    @GetMapping(value = "/{id}")
    Post getCommentById(@PathVariable("id") Long id);
}
