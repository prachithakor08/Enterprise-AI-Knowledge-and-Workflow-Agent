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

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document createDocument(Document document) {
        return documentRepository.save(document);
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Document uploadDocument(MultipartFile file, String title) throws IOException {

        String uploadDirectory = "uploads/";

        Files.createDirectories(Paths.get(uploadDirectory));

        String fileName = file.getOriginalFilename();

        Path filePath = Paths.get(uploadDirectory + fileName);

        Files.write(filePath, file.getBytes());

        Document document = new Document();

        document.setTitle(title);
        document.setFileName(fileName);
        document.setFileType(file.getContentType());
        document.setFilePath(filePath.toString());

        return documentRepository.save(document);
    }
}