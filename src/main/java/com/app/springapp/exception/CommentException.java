package com.app.springapp.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor
public class CommentException extends RuntimeException {
    private HttpStatus status;
    public CommentException(HttpStatus status, String message){
        super(message);
        this.status = status;
    }
}
