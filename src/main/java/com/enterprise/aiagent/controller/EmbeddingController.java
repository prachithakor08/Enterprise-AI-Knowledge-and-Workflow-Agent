package com.enterprise.aiagent.controller;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/embeddings")
public class EmbeddingController {

    private final EmbeddingModel embeddingModel;

    public EmbeddingController(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @GetMapping("/test")
    public Map<String, Object> testEmbedding(
            @RequestParam String text) {

        float[] embedding = embeddingModel.embed(text);

        Map<String, Object> response = new HashMap<>();

        response.put("text", text);
        response.put("dimensions", embedding.length);
        response.put("firstFiveValues",
                java.util.Arrays.copyOf(embedding, 5));

        return response;
    }
}