package com.example.demo.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "waypoint")
public class WayPoint extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;
  @Column
  private String name;
  @Column
  private String sym;
  @Column
  private BigDecimal lat;
  @Column
  private BigDecimal lon;
  @Column(name = "gpx_id")
  private Integer gpxId;

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

  public Integer getGpxId() {
    return gpxId;
  }

  public void setGpxId(Integer gpxId) {
    this.gpxId = gpxId;
  }
}
