package com.example.URLshortener;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<ShortUrl, String>{

}
