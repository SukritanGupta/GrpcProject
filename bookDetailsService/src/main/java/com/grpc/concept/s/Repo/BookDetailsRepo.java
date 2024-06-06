package com.grpc.concept.s.Repo;


import com.grpc.concept.s.Model.BookDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookDetailsRepo extends JpaRepository<BookDetailsEntity, Integer> {
}
