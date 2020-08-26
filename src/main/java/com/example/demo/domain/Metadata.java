package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "metadata")
public class Metadata extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;
  @Column
  private String name;
  @Column
  private String description;
  @Column(name = "datetime")
  private LocalDateTime dateTime;
  @Column
  private String keywords;

  @JsonIgnore
  @OneToOne(fetch = FetchType.EAGER)
  @MapsId
  @JoinColumn(name = "gpx_id", insertable = false, updatable = false)
  private GPX gpx;

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public String getKeywords() {
    return keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public GPX getGpx() {
    return gpx;
  }

  public void setGpx(GPX gpx) {
    this.gpx = gpx;
  }
}
