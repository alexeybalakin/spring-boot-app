package com.ardecs.springbootapp.repositories;

import com.ardecs.springbootapp.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
