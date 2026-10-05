package com.enterprise.aiagent.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.transformers.TransformersEmbeddingModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class EmbeddingConfig {

    @Bean
    @Primary
    public EmbeddingModel embeddingModel() {

        TransformersEmbeddingModel model = new TransformersEmbeddingModel();

        model.setModelResource(
                new FileSystemResource("models/model.onnx")
        );

        model.setTokenizerResource(
                new FileSystemResource("models/tokenizer.json")
        );

        return model;
    }
}