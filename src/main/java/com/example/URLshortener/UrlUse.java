package com.example.URLshortener;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "analytics")
public class UrlUse {

    @Id
    @GeneratedValue
    private Long id;

    @Size(max=7)
    @NotNull
    private String shortUrl;

    @NotNull
    @CreationTimestamp
    private LocalDate dateUsed;

    public UrlUse(String url) {
        this.shortUrl = url;
    }

    public UrlUse() {}

}
