package com.example.demo.repository;

import com.example.demo.domain.GPX;
import com.example.demo.domain.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, Integer> {
}
