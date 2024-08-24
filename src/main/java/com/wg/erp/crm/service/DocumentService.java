package com.wg.erp.crm.service;

import com.wg.erp.crm.model.entity.Document;
import com.wg.erp.crm.repository.DocumentRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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
    private final ResourceLoader resourceLoader;
    private static final String UPLOAD_DIR = "static/uploads/";

    public DocumentService(DocumentRepository documentRepository, ResourceLoader resourceLoader) {

        this.documentRepository = documentRepository;
        this.resourceLoader = resourceLoader;
    }

    public int getDocumentsCount() {
        return documentRepository.findAll().size();
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public void saveDocument(MultipartFile file) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:"+UPLOAD_DIR);
        byte[] bytes = file.getBytes();
        Path path = Paths.get(resource.getFile().getPath() + "/" + file.getOriginalFilename());
        Files.write(path, bytes);
    }
}
