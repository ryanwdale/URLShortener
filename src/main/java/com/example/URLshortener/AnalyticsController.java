package com.example.URLshortener;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class AnalyticsController {

    private final AnalyticsRepository analyticsRepository;
    private final UrlUseResourceAssembler urlUseAssembler;

    public AnalyticsController(AnalyticsRepository analyticsRepository, UrlUseResourceAssembler urlUseResourceAssembler) {
        this.analyticsRepository = analyticsRepository;
        this.urlUseAssembler = urlUseResourceAssembler;
    }

    @GetMapping("/analytics/{shortURL}")
    public CollectionModel<EntityModel<UrlUse>> urlUses(@PathVariable String shortURL) {
        List<EntityModel<UrlUse>> uses = analyticsRepository.findByShortenedURL(shortURL).stream()
                .map(urlUseAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(uses,
                linkTo(methodOn(AnalyticsController.class).urlUses(shortURL)).withSelfRel());
    }

}
