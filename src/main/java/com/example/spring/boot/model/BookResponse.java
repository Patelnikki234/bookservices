package com.example.spring.boot.model;

import com.example.spring.boot.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class BookResponse
{
    private boolean result;
    private String message;

    private Book data;
}
