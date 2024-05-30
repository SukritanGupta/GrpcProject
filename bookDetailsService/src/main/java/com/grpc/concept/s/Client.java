package com.grpc.concept.s;


import com.grpc.concept.s.RestDto.*;


import java.util.List;

public interface Client {
   CreateBookDetailsResponseDto createBookDet(CreateBookDetails createBookDetails);
     GetBookDetailsResponse getBookDet(GetBookDto getBookDto);

    List<GetAllBookResponseDto> getAllBookDet(GetAllBookRequestDto getAllBookRequestDto);

     void delBook(DeleteBookRequestDto deleteBookRequestDto);

    UpdateBookResponseDto updBook(UpdateBookRequestDto updateBookRequestDto,int id);
}
