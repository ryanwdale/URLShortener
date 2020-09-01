package com.example.URLshortener;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.any;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UrlControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UrlRepository urlRepository;

    @Test
    public void testGetUrls() throws Exception {
        ShortUrl url = new ShortUrl(
                "https://docs.oracle.com/javase/8/docs/api/java/util/List.html",
                "VxuFw3");

        given(urlRepository.findAll()).willReturn(Arrays.asList(url));


        mvc.perform(get("/urls")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.shortUrlList[0].shortUrl").value(url.getShortUrl()))
                .andExpect(jsonPath("$._embedded.shortUrlList[0].originalUrl").value(url.getOriginalUrl()))
                .andDo(print());
    }


    @Test
    public void testGetUrl() throws Exception {
        ShortUrl url = new ShortUrl(
                "https://docs.oracle.com/javase/8/docs/api/java/util/List.html",
                "VxuFw3");

        given(urlRepository.findById(any(String.class))).willReturn(Optional.of(url));
        mvc.perform(get("/urls/" + url.getShortUrl())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortUrl").value(url.getShortUrl()))
                .andExpect(jsonPath("$.originalUrl").value(url.getOriginalUrl()))
                .andDo(print());
    }

    @Test
    public void testShorten() throws Exception {
        ShortUrl url = new ShortUrl(
                "https://docs.oracle.com/javase/8/docs/api/java/util/List.html",
                "VxuFw3");

        given(urlRepository.save(any(ShortUrl.class))).willReturn(url);
        mvc.perform(post("/new").param("originalURL", url.getOriginalUrl())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.originalUrl").value(url.getOriginalUrl()))
                .andDo(print());
    }
}
