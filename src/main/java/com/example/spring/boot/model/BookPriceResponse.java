package com.example.spring.boot.model;

import com.example.spring.boot.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookPriceResponse {
    private String message;
    private String authorName;
    private List<Book> listBook;
}
