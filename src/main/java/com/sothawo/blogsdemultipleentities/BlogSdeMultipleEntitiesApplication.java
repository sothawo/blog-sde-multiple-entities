package com.sothawo.blogsdemultipleentities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.index.PutTemplateRequest;

@SpringBootApplication
public class BlogSdeMultipleEntitiesApplication {

    private final ElasticsearchOperations operations;

    public BlogSdeMultipleEntitiesApplication(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogSdeMultipleEntitiesApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initIndexTemplates() {
        var indexOperations = operations.indexOps(LogEntry.class);
        var putTemplateRequest = PutTemplateRequest.builder("blog-sde-log-template", "blog-sde-log-*")
            .withMappings(indexOperations.createMapping())
            .build();
        indexOperations.putTemplate(putTemplateRequest);
    }
}
