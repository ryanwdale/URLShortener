package com.example.URLshortener;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping
public class MainController {

    @Autowired
    private final URLRepository urlRepository;
    private final UrlResourceAssembler assembler;
    private final Integer URL_LENGTH = 8;
    private final Integer BASE = 62;
    private final String BASE_62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    MainController(URLRepository repository, UrlResourceAssembler assembler) {
        this.urlRepository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/urls")
    CollectionModel<EntityModel<ShortenedURL>> all() {

        List<EntityModel<ShortenedURL>> employees = urlRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(employees,
                linkTo(methodOn(MainController.class).all()).withSelfRel());
    }


    @PostMapping(path="/new")
    public ResponseEntity<?> shorten(@RequestParam String originalURL) throws URISyntaxException {
        // create string containing the users ip address and the current timestamp
        String ip = HttpReqRespUtils.getClientIpAddressIfServletRequestExist();
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        String ipAndTimeStamp = ip + timeStamp;

        byte[] bytes;
        MessageDigest md;

        // convert string to bytes and get MD5 hash instance
        try {
            bytes = ipAndTimeStamp.getBytes("UTF-8");
            md = MessageDigest.getInstance("MD5");
        }
        catch (java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 encoding unavailable");
        }

        catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 hash unavailable");
        }

        // md5 hash the original url
        byte[] hashed = md.digest(bytes);
        ByteBuffer wrapped = ByteBuffer.wrap(hashed);

        // convert to unsigned int and base 62 encode it
        String newURL = baseEncode((long) wrapped.getInt() & 0xffffffffL);
        newURL = newURL.substring(0, Math.min(newURL.length(), URL_LENGTH)); // limit url to length 7

        // insert to database and return response
        ShortenedURL url = new ShortenedURL(originalURL, newURL);
        EntityModel<ShortenedURL> resource = assembler.toModel(urlRepository.save(url));
        return ResponseEntity
                .created(new URI(resource.getLink("self").orElse(new Link("self")).getHref()))
                .body(resource);
    }

    @GetMapping(path="urls/{shortenedURL}")
    public EntityModel<ShortenedURL> getFullURL(@PathVariable String shortenedURL) {
        ShortenedURL url = urlRepository.findById(shortenedURL)
                .orElseThrow(() -> new UrlNotFoundException(shortenedURL));
        return assembler.toModel(url);
    }


    @PutMapping(path="/{shortenedURL}")
    public ResponseEntity<?> useURL(@PathVariable String shortenedURL) throws URISyntaxException {
        ShortenedURL url = urlRepository.findById(shortenedURL)
                .orElseThrow(() -> new UrlNotFoundException(shortenedURL));
        url.setTimesUsed(url.getTimesUsed()+1);

        EntityModel<ShortenedURL> resource = assembler.toModel(urlRepository.save(url));
        return ResponseEntity
                .created(new URI(resource.getLink("self").orElse(new Link("self")).getHref()))
                .body(resource);
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
