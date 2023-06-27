package com.example.spring.boot.dao;

import com.example.spring.boot.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.List;

@Repository
@EnableJpaRepositories

public interface BookRepo extends JpaRepository<Book,Integer>
{
//    @Query(value = "SELECT * FROM Book b WHERE b.author_id= :authorId",nativeQuery = true)
//    Book listBookByauthorId( @Param("authorId")int authorId);
    List<Book>findByAuthorId(int authorId);
    //searching book by random word...
    List<Book> findByBookNameContaining(String keyword);



}
