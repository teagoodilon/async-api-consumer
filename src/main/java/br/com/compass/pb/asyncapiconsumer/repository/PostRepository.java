package br.com.compass.pb.asyncapiconsumer.repository;

import br.com.compass.pb.asyncapiconsumer.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PostRepository extends JpaRepository<Post, Long>{

}
