/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.blogsdemultipleentities;

import org.springframework.data.elasticsearch.core.SearchHit;

import java.util.List;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class AdminData {
    private final List<SearchHit<Book>> books;
    private final List<SearchHit<LogEntry>> logEntries;

    AdminData(List<SearchHit<Book>> books, List<SearchHit<LogEntry>> logEntries) {
        this.books = books;
        this.logEntries = logEntries;
    }

    public List<SearchHit<Book>> getBooks() {
        return books;
    }

    public List<SearchHit<LogEntry>> getLogEntries() {
        return logEntries;
    }
}
