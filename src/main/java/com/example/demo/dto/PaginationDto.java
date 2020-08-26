package com.example.demo.dto;

public class PaginationDto {
  private int totalPages;
  private long totalRecords;
  private int limit;

  public PaginationDto(int totalPages, long totalRecords, int limit) {
    this.totalPages = totalPages;
    this.totalRecords = totalRecords;
    this.limit = limit;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public long getTotalRecords() {
    return totalRecords;
  }

  public void setTotalRecords(long totalRecords) {
    this.totalRecords = totalRecords;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }
}
