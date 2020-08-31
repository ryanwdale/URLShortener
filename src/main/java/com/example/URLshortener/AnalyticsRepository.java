package com.example.URLshortener;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AnalyticsRepository extends JpaRepository<UrlUse, String> {
    List<UrlUse> findByShortenedURL(String shortenedURL);

    List <UrlUse> findByShortenedURLAndDateUsedGreaterThanAndDateUsedLessThan(
            String shortenedURL, LocalDate startDate, LocalDate endDate);

    Long countByShortenedURLAndDateUsedGreaterThanAndDateUsedLessThan(
            String shortenedURL, LocalDate startDate, LocalDate endDate);

    @Query("select new com.example.URLshortener.UrlUsesPer(year(dateUsed), month(dateUsed), day(dateUsed), count(*), shortenedURL) " +
            "from UrlUse use where shortenedURL = ?1 group by month(dateUsed), year(dateUsed), day(dateUsed)")
    List<UrlUsesPer> countByDay(String shortURL);

    @Query("select new com.example.URLshortener.UrlUsesPer(year(dateUsed), month(dateUsed), count(*), shortenedURL) " +
            "from UrlUse use where shortenedURL = ?1 group by month(dateUsed), year(dateUsed)")
    List<UrlUsesPer> countByMonth(String shortURL);

    @Query("select new com.example.URLshortener.UrlUsesPer(year(dateUsed), count(*), shortenedURL) " +
            "from UrlUse use where shortenedURL = ?1 group by year(dateUsed)")
    List<UrlUsesPer> countByYear(String shortURL);

}
