package com.grpc.concept.s.UnitTest.ServiceLayerTest;

import com.grpc.GRPC.BookDetails;
import com.grpc.GRPC.CreateBookRequest;
import com.grpc.concept.s.Model.BookDetailsEntity;
import com.grpc.concept.s.Repo.BookDetailsRepo;
import com.grpc.concept.s.RestDto.CreateBookDetails;
//import com.grpc.concept.s.UiService.BookDetailsServ;
import com.grpc.concept.s.UiService.BookDetailsServ;
import com.grpc.concept.s.service.BookDetService;
import org.junit.gen5.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;


@ActiveProfiles("local")
@ExtendWith(MockitoExtension.class)
public class ServiceTest {
    @Mock
    private BookDetailsRepo bookDetailsRepo;
    @InjectMocks
    private BookDetService bookDetailsServ;
    private static BookDetailsEntity bookDetailsEntity;

    @BeforeEach
    public void setup(){
        bookDetailsEntity=BookDetailsEntity.builder().bookId(1).name("Rd sharma").authorName("Ravinder").price(200).build();
    }
    @Test
    void should_save_employee(){
        BDDMockito.given(bookDetailsRepo.findById(bookDetailsEntity.getBookId())).willReturn(Optional.empty());
        BDDMockito.given(bookDetailsRepo.save(bookDetailsEntity)).willReturn(bookDetailsEntity);
        Assertions.assertNotNull(  bookDetailsServ.createBooks(CreateBookRequest.newBuilder().setBookDetails(BookDetails.newBuilder().setName(bookDetailsEntity.getName()
        ).setPrice(bookDetailsEntity.getPrice()).setBookId(bookDetailsEntity.getBookId()).setAuthorName(bookDetailsEntity.getAuthorName()).build()).build())
   );
    }
}
