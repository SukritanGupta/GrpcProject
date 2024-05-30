package com.grpc.concept.s.RestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAllBookResponseDto {
    int bookId;
    String bookName;
    String bookAuthorName;
    double price;
}
