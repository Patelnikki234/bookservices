package com.example.spring.boot.service;

import com.example.spring.boot.dao.AuthorRepo;
import com.example.spring.boot.dao.BookRepo;
import com.example.spring.boot.entity.Author;
import com.example.spring.boot.entity.Book;
import com.example.spring.boot.model.BookPriceResponse;
import com.example.spring.boot.response.DeleteAuthorAndBooksResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;

@Service

public class BookService {
    @Autowired

    private BookRepo bookRepo;
    @Autowired
    AuthorRepo authorRepo;

    public ResponseEntity<String> addBook(Book book) {
        Book book1 = new Book();
        book1.setBookName(book.getBookName());
        book1.setTitle(book.getTitle());
        if (authorRepo.existsById(book.getAuthorId())) {
            book1.setAuthorId(book.getAuthorId());
            Author author = authorRepo.findById(book.getAuthorId()).get();
            String firstName = author.getFirstName();
            book1.setAuthorName(firstName);
        } else {
            return new ResponseEntity<>("Please provide valid author id!!", HttpStatus.NOT_FOUND);
        }
        //file handling
        book1.setPrice(book.getPrice());

        Book save = bookRepo.save(book1);
        if (save != null) {
            return new ResponseEntity<String>("successfully inserted data into system", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("it is not inserted", HttpStatus.NOT_FOUND);
        }

    }

    public String addAuthor(Author author) {
        authorRepo.save(author);
        return "Author added successfully...";
    }

    public List<Book> booklist(int authorId) {
        return bookRepo.findByAuthorId(authorId);


    }

    public List<Author> namelist(String authorName) {
        return authorRepo.findByFirstName(authorName);
    }

    public ResponseEntity<?> searchBook(String searchBookByName) {
        List<Book> byBookNameContaining = bookRepo.findByBookNameContaining(searchBookByName);
        if (!byBookNameContaining.isEmpty()) {
            return new ResponseEntity<>(byBookNameContaining, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No available book!", HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<String> deleteBook(int authorId) {
        System.out.println(authorId);
        if (authorRepo.existsById(authorId)) {
            authorRepo.deleteById(authorId);
            return new ResponseEntity<>("successfully deleted", HttpStatus.OK);
        }

        return new ResponseEntity<>("not exist ", HttpStatus.NOT_FOUND);
    }


    public List<Book> booklist() {
        List<Book> booklist = bookRepo.findAll();
        return booklist;

    }

    public Book retriveBook(int authorId) {
        Book book = bookRepo.findById(authorId).get();
        return book;
    }

    public ResponseEntity<?> updateBooks(String bookName, int authorId, int bookId, int price) {
        if (bookId > 0) {
            if (bookRepo.existsById(bookId)) {
                Book book1 = bookRepo.findById(bookId).get();//error/exception no value is present
                book1.setBookName(bookName);
                book1.setAuthorId(authorId);
                book1.setPrice(price);
                bookRepo.save(book1);
                return new ResponseEntity<>("Successfully updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("id is exist in db", HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("id is not valid", HttpStatus.NOT_FOUND);
        }
    }

    public Optional<Book> listBook(int bookId) {
        Optional<Book> book = bookRepo.findById(bookId);
        return book;


    }

    public ResponseEntity updatePrice(int authorId, int price) {
        List<Book> book = bookRepo.findByAuthorId(authorId);
        Author author = authorRepo.findById(authorId).get();
        List<Book> addUpdated = new ArrayList<>();
        for (Book book1 : book) {
            int bookId = book1.getBookId();
            Book book2 = bookRepo.findById(bookId).get();
            book2.setPrice(price);
            bookRepo.save(book2);
            addUpdated.add(book2);
        }
        return new ResponseEntity<>(new BookPriceResponse("successfully ", author.getFirstName() + " " + author.getLastName(), addUpdated), HttpStatus.OK);

    }

    public ResponseEntity<?> deleteAuthorAndRelatedBooks(int authorId) {
        if (authorRepo.existsById(authorId)) {
            List<Book> bookList = bookRepo.findByAuthorId(authorId);
            DeleteAuthorAndBooksResponse response = new DeleteAuthorAndBooksResponse();
            Author author = authorRepo.findById(authorId).get();
            response.setAuthorData(author);
            authorRepo.deleteById(authorId);
            List<Book> bookData = new ArrayList<>();
            if (!bookList.isEmpty()) {
                for (Book book : bookList) {
                    bookData.add(book);
                    int bookId = book.getBookId();
                    bookRepo.deleteById(bookId);
                }
                response.setBookData(bookData);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("There is no books with this userId ", HttpStatus.NOT_FOUND);
            }
        } else {

            return new ResponseEntity<>("author does not exist with these author id", HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<String> addBook(String bookName, String bookTitle, MultipartFile bookImg, int authorId, int price) throws IOException {
        Book book = new Book();
        book.setBookName(bookName);
        book.setTitle(bookTitle);
        //file handling

        String uniqueImageName = (LocalDateTime.now()).toString().replace(":", "");
        String bookImageName = uniqueImageName + bookImg.getOriginalFilename();
        book.setImageName(bookImg.getOriginalFilename());

//        File dastination=new ClassPathResource("static/bookimages").getFile();
        String imagepath = "C:/Learing springboot/Book Project/Book-Project/src/main/resources/static/bookimages";
        Path path = Paths.get(imagepath + File.separator + bookImageName);
        Files.copy(bookImg.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        bookRepo.save(book);
        return new ResponseEntity<>("Successfully file attached", HttpStatus.OK);
    }


    public ResponseEntity<?> updateAutherName(int authorId, String autherName) {
        if (authorId > 0) {
            if (authorRepo.existsById(authorId)) {
                Author author = authorRepo.findById(authorId).get();
                author.setFirstName(autherName);
                authorRepo.save(author);
                List<Book> book1 = bookRepo.findByAuthorId(authorId);
                for (Book book : book1) {
                    book.setAuthorName(autherName);
                    bookRepo.save(book);
                }
                return new ResponseEntity<>("Successfully updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("id is exist in db", HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("id is not valid", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> addBookImage(String bookName, String bookTitle, MultipartFile bookImg,
                                               int authorId, int price) throws IOException {
        Book book = new Book();
        if (authorRepo.existsById(authorId)) {
            book.setAuthorId(authorId);
            Author author = authorRepo.findById(authorId).get();
            book.setAuthorName(author.getFirstName());
        }
        book.setBookName(bookName);
        book.setTitle(bookTitle);
        //file handling
        String bookImageName = bookImg.getOriginalFilename();
        book.setImageName(bookImageName);
        book.setPrice(price);
        String imagePath = "C:/Learing springboot/Book Project/Book-Project/src/main/resources/static/bookimages";
        Path path = Paths.get(imagePath + File.separator + bookImageName);
        Files.copy(bookImg.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        bookRepo.save(book);
        return new ResponseEntity<>("Successfully file attached", HttpStatus.OK);
    }

    public ResponseEntity<String> addVideo(String bookName, String bookTitle, MultipartFile
            videoFile, int authorId, int price) throws IOException {
        Book book = new Book();
        if (authorRepo.existsById(authorId)) {
            book.setAuthorId(authorId);
            Author author = authorRepo.findById(authorId).get();
            book.setAuthorName(author.getFirstName());
        }
        book.setBookName(bookName);
        book.setTitle(bookTitle);
        String videoFileOriginalFilename = videoFile.getOriginalFilename();
        book.setVideoFile(videoFileOriginalFilename);
        book.setPrice(price);
        String videoPath = "C:/Learing springboot/Book Project/Book-Project/src/main/resources/static/bookimages";
        Path path = Paths.get(videoPath + File.separator + videoFileOriginalFilename);
        Files.copy(videoFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        bookRepo.save(book);
        return new ResponseEntity<>("Successfully videoFile Uploaded", HttpStatus.OK);
    }

    public ResponseEntity<?> getVideoPath(int bookId) {
        String url = null;
        if (bookRepo.existsById(bookId)) {
            Book book = bookRepo.findById(bookId).get();

            String videoPath = "C:/Learing springboot/Book Project/Book-Project/src/main/resources/static/bookimages/";
            String videoFile = book.getVideoFile();
             url = (videoPath+videoFile);
        }
        return new ResponseEntity<>(url, HttpStatus.OK);
    }
}




























