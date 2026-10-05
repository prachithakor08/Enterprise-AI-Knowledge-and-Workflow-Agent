package com.enterprise.aiagent.service;

import com.enterprise.aiagent.entity.Document;
import com.enterprise.aiagent.entity.DocumentChunk;
import com.enterprise.aiagent.repository.DocumentChunkRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentChunkService {

    private final DocumentChunkRepository documentChunkRepository;

    private static final int CHUNK_SIZE = 1000;

    public DocumentChunkService(DocumentChunkRepository documentChunkRepository) {
        this.documentChunkRepository = documentChunkRepository;
    }

    public List<DocumentChunk> createChunks(Document document) {

        String content = document.getContent();

        List<DocumentChunk> chunks = new ArrayList<>();

        if (content == null || content.isBlank()) {
            return chunks;
        }

        int chunkIndex = 0;

        for (int start = 0; start < content.length(); start += CHUNK_SIZE) {

            int end = Math.min(start + CHUNK_SIZE, content.length());

            String chunkContent = content.substring(start, end);

            DocumentChunk chunk =
                    new DocumentChunk(document, chunkContent, chunkIndex);

            chunks.add(chunk);

            chunkIndex++;
        }

        return documentChunkRepository.saveAll(chunks);
    }
}