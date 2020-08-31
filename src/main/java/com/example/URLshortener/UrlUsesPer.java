package com.example.URLshortener;

import lombok.Data;

@Data
public class UrlUsesPer {

    private Long count;
    private Integer day;
    private Integer month;
    private Integer year;
    private String shortenedURL;

    public UrlUsesPer(Integer year, Integer month, Integer day, Long count, String shortURL) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.count = count;
        this.shortenedURL = shortURL;
    }

    public UrlUsesPer(Integer year, Integer month, Long count, String shortURL) {
        this.year = year;
        this.month = month;
        this.count = count;
        this.shortenedURL = shortURL;
    }

    public UrlUsesPer(Integer year, Long count, String shortURL) {
        this.year = year;
        this.count = count;
        this.shortenedURL = shortURL;
    }
}
