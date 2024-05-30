package com.grpc.concept.s.RestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBookResponseDto {
    int bookId;
    String name;
    double price;
    String authorName;
}
