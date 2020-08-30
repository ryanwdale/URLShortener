package com.example.URLshortener;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "shortenedurls")
public class ShortenedURL {
    @Id
    @Size(max=7)
    @NotNull
    private String shortenedURL;

    @NotNull
    private String originalURL;


    @NotNull
    @CreationTimestamp
    private LocalDateTime created_at;

    @NotNull
    private Long timesUsed = 0L;

    public ShortenedURL(String originalURL, String shortenedURL) {
        this.originalURL = originalURL;
        this.shortenedURL = shortenedURL;
    }

    public ShortenedURL() {}

}
