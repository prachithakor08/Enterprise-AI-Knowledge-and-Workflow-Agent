package com.enterprise.aiagent.repository;

import com.enterprise.aiagent.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}