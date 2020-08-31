package com.example.URLshortener;

import lombok.Data;

@Data
public class UrlUsesPerMonth {

    private Long count;
    private Integer month;
    private Integer year;
    private String shortenedURL;

    public UrlUsesPerMonth(Integer year, Integer month, Long count, String shortURL) {
        this.year = year;
        this.month = month;
        this.count = count;
        this.shortenedURL = shortURL;
    }
}
