package com.example.demo.service;

import com.example.demo.domain.GPX;
import com.example.demo.domain.WayPoint;
import com.example.demo.dto.WayPointDto;
import com.example.demo.repository.WayPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WayPointService {
  @Autowired
  private WayPointRepository wayPointRepository;

  public List<WayPointDto> findAllByGpxId(int gpxId) {
    WayPoint example = new WayPoint();
    example.setGpxId(gpxId);
    example.setCreatedDate(null);
    example.setLastModifiedDate(null);
    List<WayPoint> wayPoints = wayPointRepository.findAll(Example.of(example));
    return wayPoints.stream().map(WayPointDto::new).collect(Collectors.toList());
  }
}
