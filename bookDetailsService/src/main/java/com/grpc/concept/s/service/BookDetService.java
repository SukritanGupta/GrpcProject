package com.grpc.concept.s.service;

import com.google.protobuf.Any;
import com.google.rpc.BadRequest;
import com.google.rpc.ErrorInfo;
import com.grpc.GRPC.*;
import com.grpc.concept.s.Model.BookDetailsEntity;
import com.grpc.concept.s.Repo.BookDetailsRepo;
import com.grpc.concept.s.apiException.AlreadyExistException;
import com.grpc.concept.s.apiException.InvalidArgumentException;
import com.grpc.concept.s.apiException.NotFoundException;
import io.grpc.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookDetService {
    @Autowired
    private BookDetailsRepo bookDetailsRepo;
    public CreateBookResponse createBooks(CreateBookRequest request){
        if(request.getBookDetails().getBookId()==0){
           throw new InvalidArgumentException("Id must not be zero ");
        }
        if(request.getBookDetails().getName().isEmpty()){
            throw new InvalidArgumentException("Name of book is required");
        }
        if(request.getBookDetails().getAuthorName().isEmpty()){
            throw new InvalidArgumentException("AuthorName is required");
        }
        if(request.getBookDetails().getPrice() ==0){
            throw new InvalidArgumentException("Price cannot be zero");
        }
        if(request.getBookDetails().getName().length()>40 || request.getBookDetails().getName().length()<2){
            throw new InvalidArgumentException("Name must not be empty");
        }
        if(request.getBookDetails().getAuthorName().length()>30 || request.getBookDetails().getAuthorName().length()<3){
            throw new InvalidArgumentException("AuthorName must be greater than 3 and less than 30");
        }
        if(bookDetailsRepo.findById(request.getBookDetails().getBookId()).isPresent()){
            ValidationError violations = ValidationError.newBuilder()
                    .setField("id")
                    .setDescription("id is already present")
                    .build();
            Any metadata = Any.pack(violations);
            BadRequest.FieldViolation fieldViolation = BadRequest.FieldViolation.newBuilder()
                    .setField(violations.getField())
                    .setDescription(violations.getDescription())
                    .build();

            throw new AlreadyExistException("Id already in used please use another id",BadRequest.newBuilder().addFieldViolations(fieldViolation).build());
        }

        CreateBookResponse createBookResponse = CreateBookResponse.newBuilder()
                .setSuccessMessage("Successfully added")
                .build();
        BookDetailsEntity bookDetailsEntity=new BookDetailsEntity();
        bookDetailsEntity.setBookId(request.getBookDetails().getBookId());
        bookDetailsEntity.setName(request.getBookDetails().getName());
        bookDetailsEntity.setAuthorName(request.getBookDetails().getAuthorName());
        bookDetailsEntity.setPrice(request.getBookDetails().getPrice());
        bookDetailsRepo.save(bookDetailsEntity);
        return createBookResponse;
    }
    public GetBookResponse getBooks(GetBookRequest request){
        Optional<BookDetailsEntity> b1= bookDetailsRepo.findById(request.getBookId());
        if(b1.isEmpty()){
            throw new NotFoundException("Sorry Book Details not found");
        }

        BookDetailsEntity bookDetailsEntity=b1.get();
        BookDetails book=BookDetails.newBuilder().setBookId(bookDetailsEntity.getBookId()).setName(bookDetailsEntity.getName()).setAuthorName(bookDetailsEntity.getAuthorName()).setPrice(bookDetailsEntity.getPrice()).build();
        GetBookResponse getBookResponse= GetBookResponse.newBuilder().setBookDetails(book).build();
        return getBookResponse;
    }
    public GetAllBookResponse getAllBooks(GetAllBooksRequest request){
        List<BookDetailsEntity> bookDetails=bookDetailsRepo.findAll();
        List<BookDetails> bookDetailsList=  bookDetails.stream().map(book->BookDetails.newBuilder().setBookId(book.getBookId()).setName(book.getName()).setAuthorName(book.getAuthorName())
                .setPrice(book.getPrice()).build()).toList();
        if(request.hasOrder()){
// later I will implement descending, according to field Name
            bookDetailsList= bookDetailsList.stream().sorted().collect(Collectors.toList());
        }
        if(request.hasSizeofPage()){
            bookDetailsList= bookDetailsList.stream().limit(request.getSizeofPage().getValue()).collect(Collectors.toList());
        }
        GetAllBookResponse getAllBookResponse=GetAllBookResponse.newBuilder().addAllBookDetails(bookDetailsList).build();
    return getAllBookResponse;
    }
    public UpdateBookResponse updateBooks(UpdateBookRequest request){
        Optional<BookDetailsEntity> b1=bookDetailsRepo.findById(request.getBookDetails().getBookId());
        if(request.getBookDetails().getBookId()==0){
            throw new InvalidArgumentException("Id must not be zero ");
        }
        if(request.getBookDetails().getName().isEmpty()){
            throw new InvalidArgumentException("Name of book is required");
        }
        if(request.getBookDetails().getAuthorName().isEmpty()){
            throw new InvalidArgumentException("AuthorName is required");
        }
        if(request.getBookDetails().getPrice() ==0){
            throw new InvalidArgumentException("Price cannot be zero");
        }
        if(request.getBookDetails().getName().length()>40 || request.getBookDetails().getName().length()<2){
            throw new InvalidArgumentException("Name must not be empty");
        }
        if(request.getBookDetails().getAuthorName().length()>30 || request.getBookDetails().getAuthorName().length()<3){
            throw new InvalidArgumentException("AuthorName must be greater than 3 and less than 30");
        }

        if(b1.isEmpty()){
//                Create a new Book and add it
            BookDetailsEntity bookDetailsEntity=BookDetailsEntity.builder().bookId(request.getBookDetails().getBookId())
                    .name(request.getBookDetails().getName()).authorName(request.getBookDetails().getAuthorName())
                    .price(request.getBookDetails().getPrice()).build();
            bookDetailsRepo.save(bookDetailsEntity);
            BookDetails bookDetails=BookDetails.newBuilder().setBookId(bookDetailsEntity.getBookId())
                    .setName(bookDetailsEntity.getName()).setAuthorName(bookDetailsEntity.getAuthorName()).setPrice(bookDetailsEntity.getPrice())
                    .build();
            UpdateBookResponse updateBookResponse=UpdateBookResponse.newBuilder().setBookDetails(bookDetails).build();
             return updateBookResponse;
        }
        else{
            b1.get().setName(request.getBookDetails().getName());
            b1.get().setPrice(request.getBookDetails().getPrice());
            b1.get().setAuthorName(request.getBookDetails().getAuthorName());
            bookDetailsRepo.save(b1.get());
            BookDetails bookDetails=BookDetails.newBuilder().setBookId(b1.get().getBookId())
                    .setName(b1.get().getName()).setAuthorName(b1.get().getAuthorName()).setPrice(b1.get().getPrice())
                    .build();
            UpdateBookResponse updateBookResponse=UpdateBookResponse.newBuilder().setBookDetails(bookDetails).build();
            return updateBookResponse;
        }
    }
    public void deleteBookMethod(DeleteBookRequest request){
        Optional<BookDetailsEntity> b1=bookDetailsRepo.findById(request.getId());
        if(b1.isEmpty()){
             throw new NotFoundException("Book not found");
        }
        bookDetailsRepo.delete(b1.get());
    }

}
