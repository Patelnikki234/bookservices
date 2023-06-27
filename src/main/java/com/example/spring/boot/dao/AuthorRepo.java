package com.example.spring.boot.dao;

import com.example.spring.boot.entity.Author;
import com.example.spring.boot.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface AuthorRepo extends JpaRepository<Author,Integer>
{
    List<Author>findByFirstName(String AuthorName);
}
