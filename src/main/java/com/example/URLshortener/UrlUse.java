package com.example.URLshortener;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    private LocalDate dateUsed;

    private Integer month;

    public UrlUse(String url) {
        this.shortenedURL = url;
        this.month = dateUsed.getMonthValue();
    }

    public UrlUse() {}

}
