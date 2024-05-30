package com.grpc.concept.s.UiService;

import com.grpc.concept.s.Client;
import com.grpc.concept.s.GrpcClient;
import com.grpc.concept.s.RestDto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookDetailsServ {
    @Autowired
    private Client grpcClient;

    public CreateBookDetailsResponseDto createB(CreateBookDetails createBookDetails){
        return grpcClient.createBookDet(createBookDetails);
    }
    public GetBookDetailsResponse   getB(int id){
        GetBookDto getBookDto=GetBookDto.builder().bookId(id).build();
        return grpcClient.getBookDet(getBookDto);
    }
    public List<GetAllBookResponseDto>  getAllB(Map<String,String> params){
        int pageSize=100; //defaultSize
        String order="";

        if(params.get("pageSize")!=null){
            pageSize=Integer.parseInt(params.get("pageSize"));
        }
        if(params.get("order")!=null){
            order=params.get("order");
        }
        GetAllBookRequestDto getAllBookRequestDto=GetAllBookRequestDto.builder().order(order).pageSize(pageSize).build();
        return grpcClient.getAllBookDet(getAllBookRequestDto);
    }

    public void deleteB(int id){
        DeleteBookRequestDto deleteBookRequestDto=DeleteBookRequestDto.builder().id(id).build();
        grpcClient.delBook(deleteBookRequestDto);
    }

    public UpdateBookResponseDto updateBookMethod(UpdateBookRequestDto updateBookRequestDto,int id){
        return grpcClient.updBook(updateBookRequestDto,id);
    }
}
