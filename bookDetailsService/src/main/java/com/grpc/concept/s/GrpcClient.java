package com.grpc.concept.s;

import com.google.protobuf.Int32Value;
import com.grpc.GRPC.*;
import com.grpc.concept.s.RestDto.*;
import com.grpc.concept.s.apiException.InvalidArgumentException;
import com.grpc.concept.s.apiException.NotFoundException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;


@Slf4j
@Service
@Profile("prod")
public class GrpcClient implements Client{
    private static ManagedChannel channel;
    private   BooKDetailsServiceGrpc.BooKDetailsServiceBlockingStub stub;

    @PostConstruct
    public void init(){
       channel= ManagedChannelBuilder.forAddress("localhost",9090).usePlaintext().build();
       stub= BooKDetailsServiceGrpc.newBlockingStub(channel);
    }
    @PreDestroy
    public void close(){
        if (channel != null) {
            log.info("Shutting down gRPC client/channel ");
            channel.shutdownNow();
        }
    }
    public CreateBookDetailsResponseDto createBookDet(CreateBookDetails createBookDetails,String token){
        log.info("Grpc client call");
        JwtCallCredentials credentials = new JwtCallCredentials(token);
            try{
                log.info("Inside the stub ");
                CreateBookRequest createBookRequest=CreateBookRequest.newBuilder().
                        setBookDetails(BookDetails.newBuilder().setBookId(createBookDetails.getBookId()).setAuthorName(createBookDetails.getAuthorName())
                                .setName(createBookDetails.getName()).setPrice(createBookDetails.getPrice())).build();
                CreateBookResponse response1=stub.withCallCredentials(credentials).createBook(createBookRequest);
                CreateBookDetailsResponseDto createBookDetailsResponseDto=CreateBookDetailsResponseDto.builder().message(response1.getSuccessMessage()).build();
                return createBookDetailsResponseDto;
            }
            catch (StatusRuntimeException statusRuntimeException){
                throw new InvalidArgumentException(statusRuntimeException.getMessage());
            }

    }
    public GetBookDetailsResponse getBookDet(GetBookDto getBookDto,String token){
        log.info("Grpc client call");
        JwtCallCredentials credentials = new JwtCallCredentials(token);
        try{
            GetBookResponse  response=stub.withCallCredentials(credentials).getBook(GetBookRequest.newBuilder().setBookId(getBookDto.getBookId()).build());
            GetBookDetailsResponse getBookDetailsResponse=GetBookDetailsResponse.builder().bookId(response.getBookDetails().getBookId()).name(response.getBookDetails().getName())
                    .authorName(response.getBookDetails().getAuthorName()).price(response.getBookDetails().getPrice()).build();
            return getBookDetailsResponse;
        }
        catch(StatusRuntimeException statusRuntimeException){
            throw new NotFoundException(statusRuntimeException.getMessage());
        }
    }

    public List<GetAllBookResponseDto> getAllBookDet(GetAllBookRequestDto getAllBookRequestDto,String token){
        log.info("Grpc client call");
        JwtCallCredentials credentials = new JwtCallCredentials(token);
        log.info("first line of getAllBook client");
        List<BookDetails> response=stub.withCallCredentials(credentials).getAllBook(GetAllBooksRequest.newBuilder().setSizeofPage(Int32Value.newBuilder().setValue(getAllBookRequestDto.getPageSize()).build()).build()).getBookDetailsList();
         List<GetAllBookResponseDto> getAllBookResponseDtos=response.stream().map(b->GetAllBookResponseDto.builder().bookId(b.getBookId()).bookName(b.getName()).bookAuthorName(b.getAuthorName()).price(b.getPrice()).build()).toList();
        return getAllBookResponseDtos;
    }


    public void delBook(DeleteBookRequestDto deleteBookRequestDto,String token){
        log.info("Grpc client call");
        JwtCallCredentials credentials = new JwtCallCredentials(token);
        try{
            stub.withCallCredentials(credentials).deleteBook(DeleteBookRequest.newBuilder().setId(deleteBookRequestDto.getId()).build());
        }
        catch(StatusRuntimeException statusRuntimeException){
            throw new NotFoundException(statusRuntimeException.getMessage());
        }

    }

    public UpdateBookResponseDto updBook(UpdateBookRequestDto updateBookRequestDto,int id,String token){
        log.info("Grpc client call");
        JwtCallCredentials credentials = new JwtCallCredentials(token);
        try{
            UpdateBookResponse response=stub.withCallCredentials(credentials).updateBook(UpdateBookRequest.newBuilder()
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
