package com.sothawo.blogsdemultipleentities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "blog-sde-log-#{T(java.time.LocalDate).now().toString()}")
public record LogEntry(
    @Id String id,
    @Field(type = FieldType.Keyword) String bookId,
    @Field(type = FieldType.Date, format = DateFormat.epoch_millis) Instant timestamp,
    @Field(type = FieldType.Text) String message
) {
    @PersistenceConstructor
    public LogEntry {
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }

    public LogEntry(String bookId, String message) {
        this(null, bookId, null, message);
    }
}
