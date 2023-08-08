package br.com.compass.pb.asyncapiconsumer.service;

import br.com.compass.pb.asyncapiconsumer.client.PostConsumerFeign;
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
        return postConsumerFeign.getPosts();
    }

    public Optional<Post> findById(Long id){
        return postRepository.findById(id);
    }

    public void create(Long id){
        Post post = new Post();
        History history = new History(Status.CREATED, LocalDateTime.now());
        created(post, history, id);
    }

    public void created(Post post, History history, Long id){
        List<History> historyList = new ArrayList<>();
        historyList.add(history);
        post.setId(id);
        post.setHistory(historyList);
        postRepository.save(post);
        postFind(post, id, historyList);
    }

    public void postFind(Post post, Long id, List<History> historyList){
        History history = new History(Status.POST_FIND, LocalDateTime.now());
        historyList.add(history);
        post.setHistory(historyList);
        postRepository.save(post);
        Post postCover = postConsumerFeign.getPostById(id);
        if(postCover != null){
            postOk(post, postCover, historyList);
        } else {
            history.setStatus(Status.FAILED);
            history.setDate(LocalDateTime.now());
            historyList.add(history);
            /////////////////////////Adicionar post FAILED
        }
    }

    public void postOk(Post post, Post postCover, List<History> historyList){
        History history = new History(Status.POST_OK, LocalDateTime.now());
        historyList.add(history);
        post.setTitle(postCover.getTitle());
        post.setBody(postCover.getBody());
        postRepository.save(post);
    }
}
