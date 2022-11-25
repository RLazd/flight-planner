package io.codelex.flightplanner.dto;

import java.util.List;

public class PageResult<T> {
    private int page;
    private int totalItems;
    private List<T> items;

    public PageResult(int page, int totalItems, List<T> items) {
        this.page = page;
        this.totalItems = totalItems;
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public List<T> getItems() {
        return items;
    }
}
