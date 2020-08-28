package com.example.URLshortener;

import java.nio.ByteBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping
public class MainController {

    @Autowired
    private URLRepository urlRepository;
    private final Integer URL_LENGTH = 8;
    private final Integer BASE = 62;
    private final String BASE_62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @PostMapping(path="/new")
    public @ResponseBody String shorten(@RequestParam String originalURL) {
        try {
            String ip = HttpReqRespUtils.getClientIpAddressIfServletRequestExist();
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
            String ipAndTimeStamp = ip + timeStamp;

            // md5 hash the original url
            byte[] bytes = ipAndTimeStamp.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashed = md.digest(bytes);
            ByteBuffer wrapped = ByteBuffer.wrap(hashed);

            // convert to unsigned int and base 62 encode it
            String newURL = baseEncode((long) wrapped.getInt() & 0xffffffffL);
            newURL = newURL.substring(0, Math.min(newURL.length(), URL_LENGTH)); // limit url to length 7

            // insert to database
            ShortenedURL url = new ShortenedURL(originalURL, newURL);

            urlRepository.save(url);
            return newURL;
        }
        catch (Exception e) {
            throw new RuntimeException("Unable to create shortened url");
        }
    }

    @GetMapping(path="/{shortenedURL}")
    public @ResponseBody String getFullURL(@PathVariable String shortenedURL) {
        ShortenedURL url = urlRepository.findById(shortenedURL)
                .orElseThrow(() -> new UrlNotFoundException(shortenedURL));
        return url.getOriginalURL();

    }

    private String baseEncode(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            int remainder = (int) (num % BASE);
            sb.append(BASE_62.charAt(remainder));
            num /= BASE;
        }
        return sb.toString();
    }
}
