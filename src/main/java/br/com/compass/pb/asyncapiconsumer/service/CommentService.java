package br.com.compass.pb.asyncapiconsumer.service;

import br.com.compass.pb.asyncapiconsumer.client.CommentConsumerFeign;
import br.com.compass.pb.asyncapiconsumer.domain.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CommentService {
    private final CommentConsumerFeign commentConsumerFeign;

    public List<Comment> getComments(){
        return commentConsumerFeign.getComments();
    }
}
