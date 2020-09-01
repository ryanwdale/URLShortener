package com.example.URLshortener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UrlRepositoryIntegrationTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    UrlRepository repository;

    @Test
    public void whenFindById_thenReturnShortUrl() {
        ShortUrl url = new ShortUrl(
                "https://docs.oracle.com/javase/8/docs/api/java/util/List.html",
                "VxuFw3");
        entityManager.persist(url);
        entityManager.flush();

        ShortUrl found = repository.findById(url.getShortUrl()).get();

        assertThat(found.getShortUrl()).isEqualTo(url.getShortUrl());


    }

}
