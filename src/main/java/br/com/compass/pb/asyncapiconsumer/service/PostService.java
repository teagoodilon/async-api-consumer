package br.com.compass.pb.asyncapiconsumer.service;

import br.com.compass.pb.asyncapiconsumer.client.PostConsumerFeign;
import br.com.compass.pb.asyncapiconsumer.entity.Comment;
import br.com.compass.pb.asyncapiconsumer.entity.History;
import br.com.compass.pb.asyncapiconsumer.entity.Post;
import br.com.compass.pb.asyncapiconsumer.util.Status;
import br.com.compass.pb.asyncapiconsumer.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostConsumerFeign postConsumerFeign;
    private final PostRepository postRepository;

    public List<Post> getPosts(){
        return postRepository.findAll();
    }

    public Optional<Post> getById(Long id){
        return postRepository.findById(id);
    }

    public List<Comment> findCommentById(Long id) {
        return postConsumerFeign.getCommentsById(id);
    }

    @Async("threadPoolTaskExecutor")
    public void created(Long id){
        Post post = new Post();
        post.setId(id);
        List<Comment> commentList = new ArrayList<>();
        List<History> historyList = new ArrayList<>();
        post.setComments(commentList);
        post.setHistory(historyList);
        historyList.add(new History(Status.CREATED, LocalDateTime.now()));
        postRepository.saveAndFlush(post);
        URI resourceUri = URI.create("/posts/" + id);
        CompletableFuture.completedFuture(ResponseEntity.created(resourceUri).body("Post created successfully"));
        postFind(post);
    }

    @Async("threadPoolTaskExecutor")
    public void postFind(Post post){
        post.getHistory().add(new History(Status.POST_FIND, LocalDateTime.now()));
        postRepository.saveAndFlush(post);
        postOk(post);
        CompletableFuture.completedFuture(null);
    }

    @Async("threadPoolTaskExecutor")
    public void postOk(Post post){
        Post postCover = postConsumerFeign.getPostById(post.getId());
        if(postCover != null){
            post.setTitle(postCover.getTitle());
            post.setBody(postCover.getBody());
            post.getHistory().add(new History(Status.POST_OK, LocalDateTime.now()));
            postRepository.saveAndFlush(post);
            commentsFind(post);
            CompletableFuture.completedFuture(null);
        } else {
            failed(post);
        }
        CompletableFuture.completedFuture(null);
    }

    @Async("threadPoolTaskExecutor")
    public void commentsFind(Post post){
        post.getHistory().add(new History(Status.COMMENTS_FIND, LocalDateTime.now()));
        postRepository.saveAndFlush(post);
        commentsOk(post);
        CompletableFuture.completedFuture(null);
    }

    @Async("threadPoolTaskExecutor")
    public void commentsOk(Post post){
        List<Comment> commentsCover = postConsumerFeign.getCommentsById(post.getId());
        if(!commentsCover.isEmpty()){
            post.setComments(commentsCover);
            post.getHistory().add(new History(Status.COMMENTS_OK, LocalDateTime.now()));
            postRepository.saveAndFlush(post);
            enable(post);
            CompletableFuture.completedFuture(null);
        } else {
            failed(post);
        }
        CompletableFuture.completedFuture(null);
    }

    @Async("threadPoolTaskExecutor")
    public void enable(Post post){
        post.getHistory().add(new History(Status.ENABLED, LocalDateTime.now()));
        postRepository.saveAndFlush(post);
        CompletableFuture.completedFuture(null);
    }

    @Async("threadPoolTaskExecutor")
    public void failed(Post post){
        post.getHistory().add(new History(Status.FAILED, LocalDateTime.now()));
        postRepository.saveAndFlush(post);
        post.getHistory().add(new History(Status.DISABLED, LocalDateTime.now()));
        postRepository.saveAndFlush(post);
        CompletableFuture.completedFuture(null);
    }

    @Async("threadPoolTaskExecutor")
    public void delete(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if(post.isPresent()){
            int size = post.get().getHistory().size() - 1;
            if(post.get().getHistory().get(size).getStatus().equals(Status.ENABLED)){
                disable(post.get());
            }
        }
        CompletableFuture.completedFuture(null);
    }

    @Async("threadPoolTaskExecutor")
    public void disable(Post post){
        post.getHistory().add(new History(Status.DISABLED, LocalDateTime.now()));
        postRepository.saveAndFlush(post);
        CompletableFuture.completedFuture(null);
    }

    @Async("threadPoolTaskExecutor")
    public void update(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if(post.isPresent()){
            int size = post.get().getHistory().size() - 1;
            if(post.get().getHistory().get(size).getStatus().equals(Status.ENABLED)||
                    post.get().getHistory().get(size).getStatus().equals(Status.DISABLED)){
                updating(post.get());
            }
        }
        CompletableFuture.completedFuture(null);
    }

    @Async("threadPoolTaskExecutor")
    public void updating(Post post) {
        post.getHistory().add(new History(Status.UPDATING, LocalDateTime.now()));
        postRepository.saveAndFlush(post);
        postFind(post);
        CompletableFuture.completedFuture(null);
    }
}
