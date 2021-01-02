package com.example.URLshortener;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<UrlUse, String> {
    List<UrlUse> findByShortUrl(String shortenedURL);

    List<UrlUse> findByShortUrlAndDateUsedGreaterThanAndDateUsedLessThan(
            String shortenedURL, LocalDate startDate, LocalDate endDate);

    Long countByShortUrlAndDateUsedGreaterThanAndDateUsedLessThan(
            String shortenedURL, LocalDate startDate, LocalDate endDate);

    @Query("select new com.example.URLshortener.UrlUsesPer(year(dateUsed), month(dateUsed), day(dateUsed), count(*), shortUrl) " +
            "from UrlUse use where shortUrl = ?1 group by month(dateUsed), year(dateUsed), day(dateUsed)")
    List<UrlUsesPer> countByDay(String shortURL);

    @Query("select new com.example.URLshortener.UrlUsesPer(year(dateUsed), month(dateUsed), count(*), shortUrl) " +
            "from UrlUse use where shortUrl = ?1 group by month(dateUsed), year(dateUsed)")
    List<UrlUsesPer> countByMonth(String shortURL);

    @Query("select new com.example.URLshortener.UrlUsesPer(year(dateUsed), count(*), shortUrl) " +
            "from UrlUse use where shortUrl = ?1 group by year(dateUsed)")
    List<UrlUsesPer> countByYear(String shortURL);

}
