package com.wg.erp.crm.service;

import com.wg.erp.crm.model.entity.Document;
import com.wg.erp.crm.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {

        this.documentRepository = documentRepository;
    }

    public int getDocumentsCount() {
        return documentRepository.findAll().size();
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }
}
