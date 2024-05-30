package com.grpc.concept.s.apiException;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotFoundException extends RuntimeException{
    private String message;
    public NotFoundException(String message){
        super(message);
        this.message=message;
    }
}
