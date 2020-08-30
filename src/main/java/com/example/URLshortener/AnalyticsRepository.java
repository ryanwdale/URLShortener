package com.example.URLshortener;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnalyticsRepository extends JpaRepository<UrlUse, String> {
    List<UrlUse> findByShortenedURL(String shortenedURL);
}
