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
public class ShortUrl {
    @Id
    @Size(max=7)
    @NotNull
    private String shortUrl;

    @NotNull
    private String originalUrl;

    @NotNull
    @CreationTimestamp
    private LocalDateTime created_at;

    public ShortUrl(String originalUrl, String shortUrl) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
    }

    public ShortUrl() {}

}
