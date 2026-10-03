package com.enterprise.aiagent.controller;

import com.enterprise.aiagent.entity.Document;
import com.enterprise.aiagent.service.DocumentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.List;


@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public Document createDocument(@RequestBody Document document) {
        return documentService.createDocument(document);
    }

    @GetMapping
    public List<Document> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @PostMapping("/upload")
    public Document uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title) throws IOException {

        return documentService.uploadDocument(file, title);
    }
}