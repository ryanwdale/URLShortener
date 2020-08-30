package com.example.URLshortener;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyticsRepository extends JpaRepository<UrlUse, String> {

}
