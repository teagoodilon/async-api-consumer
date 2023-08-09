package br.com.compass.pb.asyncapiconsumer.controller;

import br.com.compass.pb.asyncapiconsumer.domain.entity.Comment;
import br.com.compass.pb.asyncapiconsumer.domain.entity.Post;
import br.com.compass.pb.asyncapiconsumer.service.PostService;
import br.com.compass.pb.asyncapiconsumer.validation.InvalidIdNotExists;
import br.com.compass.pb.asyncapiconsumer.validation.ValidIdExists;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@Validated
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping()
    public List<Post> getPosts() {
        return postService.getPosts();
    }

    @PostMapping("/{id}")
    public void create(@PathVariable("id") @ValidIdExists @Min(1) @Max(100) Long id){
        postService.created(id);
    }

    @GetMapping("/{id}")
    public Optional<Post> getById(@PathVariable Long id) {
        return postService.getById(id);
    }

    @GetMapping("/{id}/comments")
    public List<Comment> findCommentById(@PathVariable Long id) {
        return postService.findCommentById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @InvalidIdNotExists @Min(1) @Max(100) Long id){
        postService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id){
        postService.update(id);
    }

}
