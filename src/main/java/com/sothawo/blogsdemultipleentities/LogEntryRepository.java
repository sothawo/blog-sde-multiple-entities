/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.blogsdemultipleentities;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface LogEntryRepository extends ElasticsearchRepository<LogEntry, String> {
}
