package com.sothawo.blogsdemultipleentities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.util.Objects;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "blog-sde-log-#{T(java.time.LocalDate).now().toString()}", createIndex = false)
public class LogEntry {
    @Id private final String id;
    @Field(type = FieldType.Keyword) private final String bookId;
    @Field(type = FieldType.Date, format = DateFormat.epoch_millis) private final Instant timestamp;
    @Field(type = FieldType.Text) private final String message;

    @PersistenceConstructor
    public LogEntry(String id, String bookId, Instant timestamp, String message) {
        if (timestamp == null) {
            timestamp = Instant.now();
        }
        this.id = id;
        this.bookId = bookId;
        this.timestamp = timestamp;
        this.message = message;
    }

    public LogEntry(String bookId, String message) {
        this(null, bookId, null, message);
    }

    public String getId() {
        return id;
    }

    public String getBookId() {
        return bookId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

}
