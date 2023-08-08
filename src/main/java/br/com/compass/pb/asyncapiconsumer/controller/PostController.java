package br.com.compass.pb.asyncapiconsumer.controller;

import br.com.compass.pb.asyncapiconsumer.domain.entity.Post;
import br.com.compass.pb.asyncapiconsumer.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/{postId}")
    public void create(@PathVariable Long postId){
        postService.create(postId);
    }
    @GetMapping()
    public List<Post> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/{id}")
    public Optional<Post> findById(@PathVariable Long id) {
        return postService.findById(id);
    }

}