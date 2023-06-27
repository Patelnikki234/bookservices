package com.example.spring.boot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Author")
@Setter
@Getter

public class Author
{
    @Id
  @GeneratedValue(strategy = GenerationType.AUTO)

    private int authorId;
    private String firstName;

    private String lastName;
    private String language;

}
