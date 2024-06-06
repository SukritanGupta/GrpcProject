package com.grpc.concept.s.service.config;


import com.google.protobuf.Any;
import com.google.rpc.BadRequest;
import com.google.rpc.Code;
import com.google.rpc.Status;
import com.grpc.concept.s.apiException.AlreadyExistException;
import com.grpc.concept.s.apiException.InvalidArgumentException;
import com.grpc.concept.s.apiException.NotFoundException;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.StatusProto;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@Slf4j
@GrpcAdvice
public class GrpcApiExceptionHandler {
    @GrpcExceptionHandler(InvalidArgumentException.class)
    public StatusRuntimeException handleInvalidArgumentException(InvalidArgumentException cause) {
        log.error("InvalidArgumentException", cause);
        Status status =
                Status.newBuilder()
                        .setCode(Code.INVALID_ARGUMENT_VALUE)
                        .setMessage(cause.getMessage())
                        .build();
        return StatusProto.toStatusRuntimeException(status);
    }
    @GrpcExceptionHandler(NotFoundException.class)
    public StatusRuntimeException handleNotFoundException(NotFoundException cause) {
        log.error("Not Found exception", cause);
        BadRequest badRequest=cause.getBadRequest();
        Status status =
                Status.newBuilder()
                        .setCode(Code.NOT_FOUND_VALUE)
                        .setMessage(cause.getMessage())
                        .addDetails(Any.pack(badRequest))
                        .build();
        return StatusProto.toStatusRuntimeException(status);
    }
    @GrpcExceptionHandler(AlreadyExistException.class)
    public StatusRuntimeException handleAlreadyExistException(AlreadyExistException cause) {
        log.error("AlreadyExistException", cause);
        BadRequest badRequest=cause.getBadRequest();
        Status status =
                Status.newBuilder()
                        .setCode(Code.ALREADY_EXISTS_VALUE)
                        .setMessage(cause.getMessage())
                        .addDetails(Any.pack(badRequest))
                        .build();
        return StatusProto.toStatusRuntimeException(status);
    }
}
