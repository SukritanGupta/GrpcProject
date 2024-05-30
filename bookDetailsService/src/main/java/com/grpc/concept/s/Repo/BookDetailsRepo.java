package com.grpc.concept.s.Repo;

import com.grpc.GRPC.BookDetails;
import com.grpc.concept.s.Model.BookDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.events.Event;

@Repository
public interface BookDetailsRepo extends JpaRepository<BookDetailsEntity, Integer> {
}
