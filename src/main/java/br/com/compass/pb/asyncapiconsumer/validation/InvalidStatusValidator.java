package br.com.compass.pb.asyncapiconsumer.validation;

import br.com.compass.pb.asyncapiconsumer.entity.Post;
import br.com.compass.pb.asyncapiconsumer.repository.PostRepository;
import br.com.compass.pb.asyncapiconsumer.util.Status;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class InvalidStatusValidator implements ConstraintValidator<InvalidStatus, Long> {

    @Autowired
    private PostRepository postRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        Optional<Post> post = postRepository.findById(id);
        if(post.isPresent()){
            int size = post.get().getHistory().size() - 1;
            return post.get().getHistory().get(size).getStatus().equals(Status.ENABLED);
        }
        return true;
    }

}
