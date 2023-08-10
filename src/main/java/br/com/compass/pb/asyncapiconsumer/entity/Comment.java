package br.com.compass.pb.asyncapiconsumer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Comment {
    @Id
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post")
    private Post post;

    @JsonIgnore
    private Long postId;

    @Column(length = 1000)
    private String body;
}
