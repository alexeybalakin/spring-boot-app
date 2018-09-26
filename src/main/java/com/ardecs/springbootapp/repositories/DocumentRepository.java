package com.ardecs.springbootapp.repositories;

import com.ardecs.springbootapp.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DocumentRepository extends JpaRepository<Document, Long> {

}
