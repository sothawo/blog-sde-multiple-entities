/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.blogsdemultipleentities;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;
    private final LogEntryRepository logEntryRepository;

    public BookController(BookRepository bookRepository, LogEntryRepository logEntryRepository) {
        this.bookRepository = bookRepository;
        this.logEntryRepository = logEntryRepository;
    }

    @PostMapping
    public Book post(@RequestBody Book book) {
        var savedBook = bookRepository.save(book);
        logEntryRepository.save(
            new LogEntry(savedBook.getId(), "saved book with ISBN: " + savedBook.getIsbn())
        );
        return savedBook;
    }

    @GetMapping("/{id}")
    public Book byId(@PathVariable String id) {
        return bookRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }
}
