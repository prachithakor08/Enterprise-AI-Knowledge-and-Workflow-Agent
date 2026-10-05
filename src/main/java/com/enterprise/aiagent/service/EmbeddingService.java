package com.enterprise.aiagent.service;

import com.enterprise.aiagent.entity.DocumentChunk;
import com.enterprise.aiagent.repository.DocumentChunkRepository;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmbeddingService {

    private final EmbeddingModel embeddingModel;
    private final DocumentChunkRepository chunkRepository;
    private final JdbcTemplate jdbcTemplate;

    public EmbeddingService(
            EmbeddingModel embeddingModel,
            DocumentChunkRepository chunkRepository,
            JdbcTemplate jdbcTemplate) {

        this.embeddingModel = embeddingModel;
        this.chunkRepository = chunkRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void generateEmbeddings() {

        List<DocumentChunk> chunks = chunkRepository.findAll();

        for (DocumentChunk chunk : chunks) {

            float[] vector = embeddingModel.embed(chunk.getContent());

            if (vector.length != 384) {
                throw new RuntimeException(
                        "Expected 384 dimensions but got " + vector.length
                );
            }

            String vectorString = toPgVector(vector);

            jdbcTemplate.update(
                    "UPDATE document_chunks " +
                            "SET embedding = CAST(? AS vector) " +
                            "WHERE id = ?",
                    vectorString,
                    chunk.getId()
            );

            System.out.println(
                    "Embedding generated for chunk ID: " + chunk.getId()
            );
        }

        System.out.println("All embeddings generated successfully!");
    }

    private String toPgVector(float[] vector) {

        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < vector.length; i++) {

            if (i > 0) {
                sb.append(",");
            }

            sb.append(vector[i]);
        }

        sb.append("]");

        return sb.toString();
    }
}