package com.example.spring.boot.model;

import com.example.spring.boot.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBookResponse {
    private String bookName;
    private Book bookDetails;
}
