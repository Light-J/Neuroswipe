package com.nsa.cubric.application.dto;

import java.util.List;

public class PaginatedList<T> {
    public List<T> data;
    public int totalPages;

    public PaginatedList(List<T> data, int pages) {
        this.data = data;
        this.totalPages = pages;
    }
}
