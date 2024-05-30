package com.grpc.concept.s.service;

import com.google.protobuf.Empty;
import com.grpc.GRPC.*;
import com.grpc.concept.s.service.config.LoggingInterceptor;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService( interceptors = {LoggingInterceptor.class})
@Slf4j
public class BookDetailsServiceImpl extends BooKDetailsServiceGrpc.BooKDetailsServiceImplBase {
    @Autowired
    private BookDetService bookDetService;
    @Override
    public void createBook(CreateBookRequest request, StreamObserver<CreateBookResponse> responseObserver) {
       CreateBookResponse createBookResponse=  bookDetService.createBooks(request);
            log.info("Successfully Executed create book details Service");
            responseObserver.onNext(createBookResponse);
            responseObserver.onCompleted();

    }
    @Override
    public void getBook(GetBookRequest request,StreamObserver<GetBookResponse> responseObserver){
        GetBookResponse getBookResponse=bookDetService.getBooks(request);
            responseObserver.onNext(getBookResponse);
            responseObserver.onCompleted();
    }
    @Override
    public void getAllBook(GetAllBooksRequest request,StreamObserver<GetAllBookResponse> responseObserver){
        GetAllBookResponse getAllBookResponse=bookDetService.getAllBooks(request);
        responseObserver.onNext(getAllBookResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void updateBook(UpdateBookRequest request,StreamObserver<UpdateBookResponse> responseObserver){
        UpdateBookResponse updateBookResponse=bookDetService.updateBooks(request);
        responseObserver.onNext(updateBookResponse);
            responseObserver.onCompleted();
    }
    @Override
    public void deleteBook(DeleteBookRequest request,StreamObserver<Empty> responseObserver){
            bookDetService.deleteBookMethod(request);
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();

    }
}
