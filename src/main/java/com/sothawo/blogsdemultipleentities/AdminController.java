/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.blogsdemultipleentities;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ElasticsearchOperations operations;

    public AdminController(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @GetMapping("/{id}")
    public AdminData byId(@PathVariable String id) {

        var query = new NativeSearchQueryBuilder()
            .withQuery(queryStringQuery("_id:" + id + " OR bookId:" + id))
            .build();

        var converter = operations.getElasticsearchConverter();
        List<SearchHit<Book>> books = new ArrayList<>();
        List<SearchHit<LogEntry>> logEntries = new ArrayList<>();

        SearchHits<AllDocuments> searchHits = operations.search(query, AllDocuments.class, IndexCoordinates.of("blog-sde-*"));
        searchHits.forEach(searchHit -> {

            var indexName = searchHit.getIndex();
            if (indexName != null) {
                var document = Document.from(searchHit.getContent());
                if (searchHit.getId() != null) {
                    document.setId(searchHit.getId());
                }

                if (indexName.equals("blog-sde-books")) {
                    var book = converter.read(Book.class, document);
                    books.add(searchHit(book, searchHit));
                } else if (indexName.startsWith("blog-sde-log-")) {
                    var logEntry = converter.read(LogEntry.class, document);
                    logEntries.add(searchHit(logEntry, searchHit));
                }
            }
        });

        return new AdminData(books, logEntries);
    }

    private <T> SearchHit<T> searchHit(T content, SearchHit<?> source) {
        return new SearchHit<T>(source.getIndex(),
            source.getId(),
            source.getScore(),
            source.getSortValues().toArray(new Object[0]),
            source.getHighlightFields(),
            source.getInnerHits(),
            source.getNestedMetaData(),
            content);
    }
}
