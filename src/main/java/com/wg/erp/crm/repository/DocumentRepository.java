package com.wg.erp.crm.repository;


import com.wg.erp.crm.model.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository  extends JpaRepository<Document, Long> {
}
