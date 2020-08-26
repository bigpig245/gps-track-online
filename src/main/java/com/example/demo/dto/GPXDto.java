package com.example.demo.dto;

import com.example.demo.domain.GPX;
import uk.co.jemos.podam.common.PodamExclude;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GPXDto {
  private Integer id;
  private String version;
  private String creator;
  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;
  private MetadataDto metadataDto;
  @PodamExclude
  private List<WayPointDto> wayPointDtos;

  public GPXDto() {
  }

  public GPXDto(GPX gpx) {
    this.id = gpx.getId();
    this.version = gpx.getVersion();
    this.creator = gpx.getCreator();
    this.createdDate = gpx.getCreatedDate();
    this.lastModifiedDate = gpx.getLastModifiedDate();
    if (gpx.getMetadata() != null) {
      this.metadataDto = new MetadataDto(gpx.getMetadata());
    }
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
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

  public MetadataDto getMetadataDto() {
    return metadataDto;
  }

  public void setMetadataDto(MetadataDto metadataDto) {
    this.metadataDto = metadataDto;
  }

  public List<WayPointDto> getWayPointDtos() {
    return Optional.ofNullable(wayPointDtos).orElse(Collections.emptyList());
  }

  public void setWayPointDtos(List<WayPointDto> wayPointDtos) {
    this.wayPointDtos = wayPointDtos;
  }

}
