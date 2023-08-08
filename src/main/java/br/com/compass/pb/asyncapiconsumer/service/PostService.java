package br.com.compass.pb.asyncapiconsumer.service;

import br.com.compass.pb.asyncapiconsumer.client.PostConsumerFeign;
import br.com.compass.pb.asyncapiconsumer.domain.entity.Comment;
import br.com.compass.pb.asyncapiconsumer.domain.entity.History;
import br.com.compass.pb.asyncapiconsumer.domain.entity.Post;
import br.com.compass.pb.asyncapiconsumer.util.Status;
import br.com.compass.pb.asyncapiconsumer.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void create(Long id){
        Post post = new Post();
        created(post, id);
    }

    public void created(Post post, Long id){
        post.setId(id);
        List<Comment> commentList = new ArrayList<>();
        List<History> historyList = new ArrayList<>();
        post.setComments(commentList);
        post.setHistory(historyList);
        historyList.add(new History(Status.CREATED, LocalDateTime.now()));
        postRepository.save(post);
        postFind(post);
    }

    public void postFind(Post post){
        post.getHistory().add(new History(Status.POST_FIND, LocalDateTime.now()));
        postRepository.save(post);
        postOk(post);
    }

    public void postOk(Post post){
        Post postCover = postConsumerFeign.getPostById(post.getId());
        if(postCover != null){
            post.setTitle(postCover.getTitle());
            post.setBody(postCover.getBody());
            post.getHistory().add(new History(Status.POST_OK, LocalDateTime.now()));
            postRepository.save(post);
            commentsFind(post);
        } else {
            post.getHistory().add(new History(Status.FAILED, LocalDateTime.now()));
            postRepository.save(post);
        }
    }

    public void commentsFind(Post post){
        post.getHistory().add(new History(Status.COMMENTS_FIND, LocalDateTime.now()));
        postRepository.save(post);
        commentsOk(post);
    }

    public void commentsOk(Post post){
        List<Comment> commentsCover = postConsumerFeign.getCommentsById(post.getId());
        if(!commentsCover.isEmpty()){
            post.setComments(commentsCover);
            post.getHistory().add(new History(Status.COMMENTS_OK, LocalDateTime.now()));
            postRepository.save(post);
            enable(post);
        } else {
            post.getHistory().add(new History(Status.FAILED, LocalDateTime.now()));
            postRepository.save(post);
        }
    }

    public void enable(Post post){
        post.getHistory().add(new History(Status.ENABLED, LocalDateTime.now()));
        postRepository.save(post);
    }
}
