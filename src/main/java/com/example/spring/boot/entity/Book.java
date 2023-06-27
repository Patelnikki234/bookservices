package com.example.spring.boot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Book")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Book
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int bookId;

    private String bookName;
    private String imageName;

    private String videoFile;

    private String title;
    private int authorId;

    private int price;

    private String authorName;

}
