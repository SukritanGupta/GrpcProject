package com.grpc.concept.s.apiException;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvalidArgumentException extends RuntimeException{
    private String message;

    public InvalidArgumentException(String message){
        super(message);
        this.message=message;

    }
}
