package com.example.spring.boot.controller;


import com.example.spring.boot.dao.AuthorRepo;
import com.example.spring.boot.dao.BookRepo;
import com.example.spring.boot.entity.Author;
import com.example.spring.boot.entity.Book;
import com.example.spring.boot.model.BookResponse;
import com.example.spring.boot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")

public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorRepo authorRepo;
    @Autowired
    private BookRepo bookRepo;


    @PostMapping("/addBook")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        return bookService.addBook(book);

    }


    @PostMapping("/addAuthor")
    public String addAuthor(@RequestBody Author author) {

        return bookService.addAuthor(author);
    }

    @GetMapping("/retrive")
    public List<Book> booklist(@RequestParam("authorId") int authorId) {
        System.out.println(authorId);
        return bookService.booklist(authorId);

    }

    @GetMapping("/retriveName")
    public List<Author> authors(@RequestParam("firstName") String firstName) {
        System.out.println(firstName);
        return bookService.namelist(firstName);
    }

    @GetMapping("/searchBook")
    public ResponseEntity<?> searchBooks(@RequestParam(name = "search") String key) {
        return bookService.searchBook(key);
    }

    @DeleteMapping("/delete/{authorId}")
    public ResponseEntity<String> delete(@PathVariable int authorId) {
        return bookService.deleteBook(authorId);
    }


    @GetMapping("/getListOfBooks")
    public ResponseEntity listOfBooks() {
        List<Book> listofBook = bookService.booklist();
        for (Book book : listofBook) {
            int bid = book.getBookId();
            String title = book.getTitle();
            System.out.println(bid);
            System.out.println(title);
        }
        return new ResponseEntity<>(listofBook, HttpStatus.OK);
    }


    @GetMapping("/getAuthorById")
    public ResponseEntity<?> retrieve(@RequestParam("authorId") int authorId) {

        Book book = bookService.retriveBook(authorId);
        return new ResponseEntity<>(new BookResponse(true, "successfully retive!!", book), HttpStatus.OK);

    }

    @PutMapping("/updateBook")
    public ResponseEntity<?> bookUpdate(@RequestParam(name = "bookName") String bookname,
                                        @RequestParam(name = "authorId") int authorId,
                                        @RequestParam(name = "id") int bookId,
                                        @RequestParam(name = "price") int price) {
        return bookService.updateBooks(bookname, authorId, bookId, price);
    }


    @GetMapping("/retriveBook")
    public Optional<Book> listOfBook(@RequestParam(name = "bookId") int bookId) {
        return bookService.listBook(bookId);

    }

    @PutMapping("/updatePrice")
    public ResponseEntity bookList(@RequestParam(name = "authorId") int authorId, @RequestParam(name = "price") int price) {
        return bookService.updatePrice(authorId, price);
    }

    //    @DeleteMapping("/deleteAuthorById")
//    public ResponseEntity<String> deleteAuthor(@RequestParam(name = "authorId") int authorId) {
//        return bookService.deleteByAuthor(authorId);
//    }
    @DeleteMapping("/deleteAuthorAndBooks/{authorId}")
    public ResponseEntity<String> deleteAuthorAndBooks(@PathVariable int authorId) {
        return (ResponseEntity<String>) bookService.deleteAuthorAndRelatedBooks(authorId);
    }

    @PostMapping("/addFile")
    public ResponseEntity addBook(@RequestParam(name = "bookName") String bookName,
                                  @RequestParam(name = "bookTitle") String booktitle,
                                  @RequestParam(name = "image") MultipartFile bookImage,
                                  @RequestParam(name = "authorId") int authorId,
                                  @RequestParam(name = "price") int bookPrice) throws IOException {
        return bookService.addBook(bookName, booktitle, bookImage, authorId, bookPrice);
    }

    @PutMapping("/updateByAutherName")
    public ResponseEntity<?> updateByAuthorName(@RequestParam(name = "authorName") String authorName,
                                                @RequestParam(name = "authorId") int authorId) {

        return bookService.updateAutherName(authorId, authorName);
    }


    @PostMapping("/addImage")
    public ResponseEntity addNewImage(@RequestParam(name = "bookName") String bookName,
                                  @RequestParam(name = "bookTitle") String booktitle,
                                  @RequestParam(name = "image") MultipartFile bookImage,
                                  @RequestParam(name = "authorId") int authorId,
                                  @RequestParam(name = "price") int price) throws IOException {
        return bookService.addBookImage(bookName, booktitle, bookImage, authorId, price);
    }

    @PostMapping("/addVideo")
    public ResponseEntity addVideo(@RequestParam(name = "bookName") String bookName,
                                   @RequestParam(name = "bookTitle") String booktitle,
                                   @RequestParam(name = "image") MultipartFile bookImage,
                                   @RequestParam(name = "authorId") int authorId,
                                   @RequestParam(name = "price") int price) throws IOException {
        return bookService.addVideo(bookName, booktitle, bookImage, authorId, price);
    }
    @GetMapping("/getUrl")
    public ResponseEntity addUrl(@RequestParam(name = "BookId") int bookId)
    {
        return bookService.getVideoPath(bookId);

    }

}












