package com.grpc.concept.s.UnitTest.DaoLayer;

import com.grpc.concept.s.GrpcApplication;
import com.grpc.concept.s.Model.BookDetailsEntity;
import com.grpc.concept.s.Repo.BookDetailsRepo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ActiveProfiles("local")
@DataJpaTest
public class DaoTest {
    @Autowired
    private BookDetailsRepo bookDetailsRepo;
    private static      BookDetailsEntity bookDetailsEntity;
    @BeforeEach
    public void setup(){
       bookDetailsEntity=BookDetailsEntity.builder().bookId(1).name("Rd sharma").authorName("Ravinder").price(200).build();
    }
    @Test
    @DisplayName("User should be able to save the book")
    void should_save_data(){
      BookDetailsEntity bookDetailsEntity1=bookDetailsRepo.save(bookDetailsEntity);
        Assertions.assertNotNull(bookDetailsEntity1);
    }
}
