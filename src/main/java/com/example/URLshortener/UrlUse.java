package com.example.URLshortener;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "analytics")
public class UrlUse {


    @Id
    @GeneratedValue
    private Long id;

    @Size(max=7)
    @NotNull
    private String shortenedURL;

    @NotNull
    @CreationTimestamp
    private LocalDateTime timeUsed;

    public UrlUse(String shortenedURL) {
        this.shortenedURL = shortenedURL;
    }

    public UrlUse() {}

}
