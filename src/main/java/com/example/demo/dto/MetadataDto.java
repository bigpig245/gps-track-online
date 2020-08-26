package com.example.demo.dto;

import com.example.demo.domain.Metadata;

import java.time.LocalDateTime;

public class MetadataDto {
  private Integer id;
  private String name;
  private String description;
  private LocalDateTime dateTime;
  private String keywords;
  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;

  public MetadataDto() {
  }

  public MetadataDto(Metadata metadata) {
    this.id = metadata.getId();
    this.name = metadata.getName();
    this.description = metadata.getDescription();
    this.dateTime = metadata.getDateTime();
    this.keywords = metadata.getKeywords();
    this.createdDate = metadata.getCreatedDate();
    this.lastModifiedDate = metadata.getLastModifiedDate();
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
