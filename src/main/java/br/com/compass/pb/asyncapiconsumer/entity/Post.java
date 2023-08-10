package br.com.compass.pb.asyncapiconsumer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    private Long id;
    private String title;
    private String body;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<History> history;
}
