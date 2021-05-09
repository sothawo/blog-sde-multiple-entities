package com.sothawo.blogsdemultipleentities;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Objects;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "blog-sde-books")
public record Book(@Id String id,
                   @Field(type = FieldType.Text) String author,
                   @Field(type = FieldType.Text) String title,
                   @Field(type = FieldType.Keyword) String isbn) {
}
