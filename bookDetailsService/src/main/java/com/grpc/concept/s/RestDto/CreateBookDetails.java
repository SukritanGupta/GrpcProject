package com.grpc.concept.s.RestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CreateBookDetails {
    int bookId;
    String name;
    double price;
    String authorName;
}
