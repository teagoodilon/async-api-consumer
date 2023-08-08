package br.com.compass.pb.asyncapiconsumer.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Comment {
    @Id
    private Long id;
    private Long postId;
    private String body;
}
