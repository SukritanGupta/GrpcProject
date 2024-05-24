package com.grpc.concept.s.service;

import com.grpc.GRPC.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BookDetailsServiceImpl extends BooKDetailsServiceGrpc.BooKDetailsServiceImplBase {
    @Override
    public void createBook(CreateBookRequest request, StreamObserver<CreateBookResponse> responseObserver) {
       CreateBookResponse createBookResponse = CreateBookResponse.newBuilder()
               .setSuccessMessage("Successfully added")
                .build();
        responseObserver.onNext(createBookResponse);
        responseObserver.onCompleted();
    }
    @Override
    public void getBook(GetBookRequest request,StreamObserver<GetBookResponse> responseObserver){
        GetBookResponse getBookResponse= GetBookResponse.newBuilder().setBookDetails(BookDetails.newBuilder().setBookId(request.getBookId()).build()).build();
        responseObserver.onNext(getBookResponse);
        responseObserver.onCompleted();
    }
}
