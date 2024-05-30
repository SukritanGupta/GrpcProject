package com.grpc.concept.s.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDetailsEntity {
    @Id
    int bookId;
    String name;
    double price;
    String authorName;

}
