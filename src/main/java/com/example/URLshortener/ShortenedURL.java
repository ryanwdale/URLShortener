package com.example.URLshortener;

import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.Size;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.persistence.Table;

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

    public ShortenedURL(String originalURL, String shortenedURL) {
        this.originalURL = originalURL;
        this.shortenedURL = shortenedURL;
    }

    public ShortenedURL() {}

}
