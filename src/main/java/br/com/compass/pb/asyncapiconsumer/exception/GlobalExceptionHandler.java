package br.com.compass.pb.asyncapiconsumer.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleInvalidId(ConstraintViolationException ex) {
        String message = ex.getLocalizedMessage();
        if(message.equals("create.id: This id has already been registered in the database") ||
                message.equals("delete.id: This id has not yet been registered in the database")){
            HttpStatus status = HttpStatus.BAD_REQUEST;
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
            problemDetail.setTitle("Bad Request");
            return ResponseEntity.status(status).body(problemDetail);
        }
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        problemDetail.setTitle("Not Acceptable");
        return ResponseEntity.status(status).body(problemDetail);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDetail> handleInvalidRequest(MethodArgumentTypeMismatchException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problemDetail.setTitle("Bad Request");
        return ResponseEntity.status(status).body(problemDetail);
    }

}
