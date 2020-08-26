package com.example.demo.dto;

import com.example.demo.domain.WayPoint;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WayPointDto {
  private Integer id;
  private String name;
  private String sym;
  private BigDecimal lat;
  private BigDecimal lon;
  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;

  public WayPointDto() {
  }

  public WayPointDto(WayPoint wayPoint) {
    this.id = wayPoint.getId();
    this.name = wayPoint.getName();
    this.sym = wayPoint.getSym();
    this.lat = wayPoint.getLat();
    this.lon = wayPoint.getLon();
    this.createdDate = wayPoint.getCreatedDate();
    this.lastModifiedDate = wayPoint.getLastModifiedDate();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSym() {
    return sym;
  }

  public void setSym(String sym) {
    this.sym = sym;
  }

  public BigDecimal getLat() {
    return lat;
  }

  public void setLat(BigDecimal lat) {
    this.lat = lat;
  }

  public BigDecimal getLon() {
    return lon;
  }

  public void setLon(BigDecimal lon) {
    this.lon = lon;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public LocalDateTime getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }
}
