package com.grpc.concept.s.apiException;

import com.google.rpc.BadRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotFoundException extends RuntimeException{
    private String message;
    private BadRequest badRequest;
    public NotFoundException(String message,BadRequest badRequest){
        super(message);
        this.message=message;
        this.badRequest=badRequest;
    }
}
