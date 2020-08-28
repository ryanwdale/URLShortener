package com.example.URLshortener;

public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException(String url) {
        super("could not find url " + url);
    }
}
