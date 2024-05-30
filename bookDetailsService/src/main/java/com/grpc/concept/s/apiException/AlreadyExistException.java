package com.grpc.concept.s.apiException;

import com.google.rpc.BadRequest;
import lombok.Data;

@Data
public class AlreadyExistException extends RuntimeException{
    private String message;
    private BadRequest badRequest;
    public AlreadyExistException(String message,BadRequest badRequest){
        super(message);
        this.message=message;
        this.badRequest=badRequest;
    }
}
