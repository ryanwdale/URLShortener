package com.example.URLshortener;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public ShortenedURL(String originalURL, String shortenedURL) {
        this.originalURL = originalURL;
        this.shortenedURL = shortenedURL;
    }

    public ShortenedURL() {}

}
