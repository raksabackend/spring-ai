package org.example.springai.dto;

import java.util.List;

public record DocumentSummary(
        String title,
        String category,
        List<String> keyTopics,
        String targetAudience
) {}
