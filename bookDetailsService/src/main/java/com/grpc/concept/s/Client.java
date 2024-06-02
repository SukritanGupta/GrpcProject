package com.grpc.concept.s;


import com.grpc.concept.s.RestDto.*;


import java.util.List;

public interface Client {
   CreateBookDetailsResponseDto createBookDet(CreateBookDetails createBookDetails,String token);
     GetBookDetailsResponse getBookDet(GetBookDto getBookDto,String token);

    List<GetAllBookResponseDto> getAllBookDet(GetAllBookRequestDto getAllBookRequestDto,String token);

     void delBook(DeleteBookRequestDto deleteBookRequestDto,String token);

    UpdateBookResponseDto updBook(UpdateBookRequestDto updateBookRequestDto,int id,String token);
}
