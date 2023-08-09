package br.com.compass.pb.asyncapiconsumer.validation;

import br.com.compass.pb.asyncapiconsumer.repository.PostRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class IdNotExistsValidator implements ConstraintValidator<InvalidIdNotExists, Long> {

    @Autowired
    private PostRepository postRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        return id == null || postRepository.findById(id).isPresent();
    }

}