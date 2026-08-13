package org.example.springai.config;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class DocumentTools {
    private final IngestionStats ingestionStats;

    public DocumentTools(IngestionStats ingestionStats) {
        this.ingestionStats = ingestionStats;
    }

    @Tool(description = "Get metadata about the currently indexed document, including " +
            "its filename, total page count, and how many chunks it was split into for search.")
    public String getDocumentMetadata() {
        return "Source file: %s, Pages: %d, Indexed chunks: %d".formatted(
                ingestionStats.getSourceFileName(),
                ingestionStats.getPageCount(),
                ingestionStats.getChunkCount());
    }
}
