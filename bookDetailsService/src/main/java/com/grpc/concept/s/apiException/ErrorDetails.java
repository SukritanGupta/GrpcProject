package com.grpc.concept.s.apiException;

import java.time.LocalDateTime;

public class ErrorDetails {// this class I have use to create the error respone
    private LocalDateTime timestamp;
    private String message;
    private String details;
    public ErrorDetails(LocalDateTime localDateTime, String message, String details) {
        super();
        this.timestamp = localDateTime;
        this.message = message;
        this.details = details;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public String getMessage() {
        return message;
    }
    public String getDetails() {
        return details;
    }
}
