package com.project.openbook.controller;

import com.project.openbook.model.Book;
import com.project.openbook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;


@RestController
@RequestMapping("api/books")
public class BookController {

    private BookRepository bookRepository;
    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping()
    public Page<Book> getAllBooks(@RequestParam(name = "page") int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("bookid").ascending());
        return bookRepository.findAll(pageable);
    }

    @GetMapping("search")
    public Page<Book> getBooksByFilter(@RequestParam(name = "page") int pageNumber, @RequestParam(name = "search") String search) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("title").ascending());
        return bookRepository.findBooksByTitleIgnoreCaseContaining(search, pageable);
    }

    @GetMapping("{bookId}")
    public Book getBookById(@PathVariable String bookId) {
        try {
            return bookRepository.findById(Integer.parseInt(bookId)).get();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Book with specific ID doesn't exist");
        }
    }
}
