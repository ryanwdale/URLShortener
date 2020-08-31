package com.example.URLshortener;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface AnalyticsRepository extends JpaRepository<UrlUse, String> {
    List<UrlUse> findByShortenedURL(String shortenedURL);

    List <UrlUse> findByShortenedURLAndDateUsedGreaterThanAndDateUsedLessThan(
            String shortenedURL, LocalDate startDate, LocalDate endDate);

    Long countByShortenedURLAndDateUsedGreaterThanAndDateUsedLessThan(
            String shortenedURL, LocalDate startDate, LocalDate endDate);

    @Query("select new com.example.URLshortener.UrlUsesPerMonth(year(dateUsed), month(dateUsed), count(*), shortenedURL) " +
            "from UrlUse use where shortenedURL = ?1 group by month(dateUsed), year(dateUsed)")
    List<UrlUsesPerMonth> countByMonth(String shortURL);

}
