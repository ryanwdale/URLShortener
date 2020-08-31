package com.example.URLshortener;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UrlCount {

    private String shortenedURL;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long count;

    public UrlCount(String url, LocalDate start, LocalDate end, Long count) {
        this.shortenedURL = url;
        this.startDate = start;
        this.endDate = end;
        this.count = count;
    }

    public UrlCount() {}

}
