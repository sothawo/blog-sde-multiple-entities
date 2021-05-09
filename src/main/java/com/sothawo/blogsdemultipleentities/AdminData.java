/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.blogsdemultipleentities;

import org.springframework.data.elasticsearch.core.SearchHit;

import java.util.List;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
record AdminData(
    List<SearchHit<Book>> books,
    List<SearchHit<LogEntry>> logEntries
) {
}
