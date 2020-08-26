package com.example.demo.dto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PageDataDto<T> {
  private List<T> contents;
  private PaginationDto page;

  public PageDataDto() {
  }

  public PageDataDto(List<T> contents, PaginationDto page) {
    this.contents = contents;
    this.page = page;
  }

  public List<T> getContents() {
    return Optional.ofNullable(contents).orElse(Collections.emptyList());
  }

  public void setContents(List<T> contents) {
    this.contents = contents;
  }

  public PaginationDto getPage() {
    return page;
  }

  public void setPage(PaginationDto page) {
    this.page = page;
  }
}
