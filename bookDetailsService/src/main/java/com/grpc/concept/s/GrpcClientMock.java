package com.grpc.concept.s;

import com.google.protobuf.Int32Value;
import com.grpc.GRPC.*;
import com.grpc.concept.s.RestDto.*;
import com.grpc.concept.s.apiException.InvalidArgumentException;
import com.grpc.concept.s.apiException.NotFoundException;
import com.grpc.concept.s.service.BookDetService;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile("local")
//Basically for the concept like with the local profile h2 non persist database is used
public class GrpcClientMock implements Client {
    final BookDetService client;

    @Override
    public CreateBookDetailsResponseDto createBookDet(CreateBookDetails createBookDetails,String token) {
        log.info("Mock create Book call");
        try{
            CreateBookRequest createBookRequest=CreateBookRequest.newBuilder().
                    setBookDetails(BookDetails.newBuilder().setBookId(createBookDetails.getBookId()).setAuthorName(createBookDetails.getAuthorName())
                            .setName(createBookDetails.getName()).setPrice(createBookDetails.getPrice())).build();
            CreateBookResponse response1=client.createBooks(createBookRequest);
            CreateBookDetailsResponseDto createBookDetailsResponseDto=CreateBookDetailsResponseDto.builder().message(response1.getSuccessMessage()).build();
            return createBookDetailsResponseDto;
        }
        catch (StatusRuntimeException statusRuntimeException){
            throw new InvalidArgumentException(statusRuntimeException.getMessage());
        }
    }

    @Override
    public GetBookDetailsResponse getBookDet(GetBookDto getBookDto,String token) {
        log.info("mock getBookDet call");
        try{
            GetBookResponse response=client.getBooks(GetBookRequest.newBuilder().setBookId(getBookDto.getBookId()).build());
            GetBookDetailsResponse getBookDetailsResponse=GetBookDetailsResponse.builder().bookId(response.getBookDetails().getBookId()).name(response.getBookDetails().getName())
                    .authorName(response.getBookDetails().getAuthorName()).price(response.getBookDetails().getPrice()).build();
            return getBookDetailsResponse;
        }
        catch(StatusRuntimeException statusRuntimeException){
            throw new NotFoundException(statusRuntimeException.getMessage());
        }
    }

    @Override
    public List<GetAllBookResponseDto> getAllBookDet(GetAllBookRequestDto getAllBookRequestDto,String token) {
        log.info("mock getAllBookDet call");
        List<BookDetails> response=client.getAllBooks(GetAllBooksRequest.newBuilder().setSizeofPage(Int32Value.newBuilder().setValue(getAllBookRequestDto.getPageSize()).build()).build()).getBookDetailsList();
        List<GetAllBookResponseDto> getAllBookResponseDtos=response.stream().map(b->GetAllBookResponseDto.builder().bookId(b.getBookId()).bookName(b.getName()).bookAuthorName(b.getAuthorName()).price(b.getPrice()).build()).toList();
        return getAllBookResponseDtos;
    }

    @Override
    public void delBook(DeleteBookRequestDto deleteBookRequestDto,String token) {
        log.info("mock delBookDet call");
        try{
            client.deleteBookMethod(DeleteBookRequest.newBuilder().setId(deleteBookRequestDto.getId()).build());
        }
        catch(StatusRuntimeException statusRuntimeException){
            throw new NotFoundException(statusRuntimeException.getMessage());
        }

    }

    @Override
    public UpdateBookResponseDto updBook(UpdateBookRequestDto updateBookRequestDto, int id,String token) {
        log.info("mock updateBookDet call");
        try{
            UpdateBookResponse response=client.updateBooks(UpdateBookRequest.newBuilder()
                    .setBookDetails(BookDetails.newBuilder().setBookId(id)
                            .setName(updateBookRequestDto.getName()).setAuthorName(updateBookRequestDto.getAuthorName()).setPrice(updateBookRequestDto.getPrice()).build()).build());
            UpdateBookResponseDto updateBookResponseDto=UpdateBookResponseDto.builder().bookId(response.getBookDetails().getBookId())
                    .authorName(response.getBookDetails().getAuthorName()).name(response.getBookDetails().getName()).price(response.getBookDetails().getPrice()).build();
            return updateBookResponseDto;
        }
        catch(StatusRuntimeException statusRuntimeException){
            throw new InvalidArgumentException(statusRuntimeException.getMessage());
        }

    }
}
