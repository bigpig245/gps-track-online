package com.example.demo.domain;

import javax.persistence.*;

@Entity
@Table(name = "gpx")
public class GPX extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;
  @Column(name = "version", nullable = false)
  private String version;
  @Column(name = "creator", nullable = false)
  private String creator;
  @OneToOne(mappedBy = "gpx", cascade = CascadeType.ALL)
  private Metadata metadata;

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

  public Metadata getMetadata() {
    return metadata;
  }

  public void setMetadata(Metadata metadata) {
    this.metadata = metadata;
  }
}
