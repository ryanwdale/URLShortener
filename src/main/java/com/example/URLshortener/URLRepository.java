package com.example.URLshortener;

import org.springframework.data.jpa.repository.JpaRepository;

public interface URLRepository extends JpaRepository<ShortenedURL, String>{

}
