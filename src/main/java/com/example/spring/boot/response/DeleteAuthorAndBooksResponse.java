package com.example.spring.boot.response;

import com.example.spring.boot.entity.Author;
import com.example.spring.boot.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DeleteAuthorAndBooksResponse
{
    Author authorData;
    List<Book> bookData;
}
