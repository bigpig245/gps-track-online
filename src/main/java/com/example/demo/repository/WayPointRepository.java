package com.example.demo.repository;

import com.example.demo.domain.GPX;
import com.example.demo.domain.WayPoint;
import com.example.demo.dto.WayPointDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WayPointRepository extends JpaRepository<WayPoint, Integer> {
}
