package org.example.springai.config;

import org.springframework.stereotype.Component;

@Component
public class IngestionStats {
    private volatile int pageCount;
    private volatile int chunkCount;
    private volatile String sourceFileName;

    public void record(int pageCount, int chunkCount, String sourceFileName) {
        this.pageCount = pageCount;
        this.chunkCount = chunkCount;
        this.sourceFileName = sourceFileName;
    }

    public int getPageCount() { return pageCount; }
    public int getChunkCount() { return chunkCount; }
    public String getSourceFileName() { return sourceFileName; }
}
