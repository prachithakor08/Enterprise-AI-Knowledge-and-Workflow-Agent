package com.enterprise.aiagent.service;

import com.enterprise.aiagent.entity.Document;
import com.enterprise.aiagent.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;


@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentChunkService documentChunkService;

    public DocumentService(
            DocumentRepository documentRepository,
            DocumentChunkService documentChunkService) {

        this.documentRepository = documentRepository;
        this.documentChunkService = documentChunkService;
    }
    public Document createDocument(Document document) {
        return documentRepository.save(document);
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Document uploadDocument(MultipartFile file, String title)
            throws IOException, TikaException {

        String uploadDirectory = "uploads/";

        Files.createDirectories(Paths.get(uploadDirectory));

        String fileName = file.getOriginalFilename();

        Path filePath = Paths.get(uploadDirectory + fileName);

        Files.write(filePath, file.getBytes());

        String extractedText = extractText(file);

        Document document = new Document();

        document.setTitle(title);
        document.setFileName(fileName);
        document.setFileType(file.getContentType());
        document.setFilePath(filePath.toString());
        document.setContent(extractedText);

        Document savedDocument = documentRepository.save(document);

        documentChunkService.createChunks(savedDocument);

        return savedDocument;
    }

    public String extractText(MultipartFile file) throws IOException, TikaException {

        Tika tika = new Tika();

        String text = tika.parseToString(file.getInputStream());

        return text;
    }
}