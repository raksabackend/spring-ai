package org.example.springai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.springai.config.IngestionStats;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.io.Resource;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PdfIngestionRunner implements ApplicationRunner  {
    private final VectorStore vectorStore;
    private final IngestionStats ingestionStats;

    @Value("${app.pdf.path}")
    private Resource pdfResource;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Ingesting PDF into vector store...");

        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(
                pdfResource,
                PdfDocumentReaderConfig.builder()
                        .withPageExtractedTextFormatter(
                                ExtractedTextFormatter.builder()
                                        .withNumberOfBottomTextLinesToDelete(0)
                                        .build())
                        .withPagesPerDocument(1)
                        .build());

        List<Document> pages = pdfReader.get();

        TokenTextSplitter splitter = new TokenTextSplitter();
        List<Document> chunks = splitter.apply(pages);

        vectorStore.add(chunks);
        log.info("Ingested {} chunks from {} pages", chunks.size(), pages.size());
        ingestionStats.record(pages.size(), chunks.size(), pdfResource.getFilename());
    }


}
