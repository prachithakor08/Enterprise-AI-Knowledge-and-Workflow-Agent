package com.enterprise.aiagent.controller;

import com.enterprise.aiagent.entity.Document;
import com.enterprise.aiagent.service.DocumentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.enterprise.aiagent.entity.DocumentChunk;
import com.enterprise.aiagent.repository.DocumentChunkRepository;
import com.enterprise.aiagent.service.EmbeddingService;

import java.io.IOException;
import org.apache.tika.exception.TikaException;

import java.util.List;


@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    private final DocumentChunkRepository documentChunkRepository;
    private final EmbeddingService embeddingService;

    public DocumentController(
            DocumentService documentService,
            DocumentChunkRepository documentChunkRepository,
            EmbeddingService embeddingService) {

        this.documentService = documentService;
        this.documentChunkRepository = documentChunkRepository;
        this.embeddingService = embeddingService;
    }

    @GetMapping("/chunks")
    public List<DocumentChunk> getAllChunks() {
        return documentChunkRepository.findAll();
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
            @RequestParam("title") String title)
            throws IOException, TikaException{

        return documentService.uploadDocument(file, title);
    }

    @PostMapping("/extract-text")
    public String extractText(@RequestParam("file") MultipartFile file)
            throws IOException, TikaException {

        return documentService.extractText(file);
    }

    @PostMapping("/generate-embeddings")
    public String generateEmbeddings() {

        embeddingService.generateEmbeddings();

        return "Embeddings generated successfully!";
    }
}