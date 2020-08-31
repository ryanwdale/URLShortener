package com.example.URLshortener;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class AnalyticsController {

    private final AnalyticsRepository analyticsRepository;
    private final UrlUseResourceAssembler urlUseAssembler;
    private final UrlCountResourceAssembler urlCountAssembler;
    private final UsesPerResourceAssembler perMonthAssembler;

    public AnalyticsController(AnalyticsRepository analyticsRepository,
                               UrlUseResourceAssembler urlUseResourceAssembler,
                               UrlCountResourceAssembler urlCountAssembler,
                               UsesPerResourceAssembler perMonthAssembler) {
        this.analyticsRepository = analyticsRepository;
        this.urlUseAssembler = urlUseResourceAssembler;
        this.urlCountAssembler = urlCountAssembler;
        this.perMonthAssembler = perMonthAssembler;
    }

    @GetMapping("/analytics/{shortURL}")
    public CollectionModel<EntityModel<UrlUse>> urlUses(@PathVariable String shortURL) {
        List<EntityModel<UrlUse>> uses = analyticsRepository.findByShortUrl(shortURL).stream()
                .map(urlUseAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(uses,
                linkTo(methodOn(AnalyticsController.class).urlUses(shortURL)).withSelfRel());
    }

    @GetMapping("/analytics/dates/{shortURL}")
    public CollectionModel<EntityModel<UrlUse>> usesBetweenDates(
            @PathVariable String shortURL,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam LocalDate start,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam LocalDate end) {

        List<EntityModel<UrlUse>> uses = analyticsRepository
                .findByShortUrlAndDateUsedGreaterThanAndDateUsedLessThan(shortURL, start, end)
                .stream()
                .map(urlUseAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(uses,
                linkTo(methodOn(AnalyticsController.class).urlUses(shortURL)).withSelfRel());
    }



    @GetMapping("/analytics/counts/{shortURL}")
    public EntityModel<UrlCount> countBetweenDates(
            @PathVariable String shortURL,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam LocalDate start,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam LocalDate end) {

        Long count = analyticsRepository
                .countByShortUrlAndDateUsedGreaterThanAndDateUsedLessThan(shortURL, start, end);

        return urlCountAssembler.toModel(new UrlCount(shortURL, start, end, count));
    }

    @GetMapping("/analytics/years/{shortURL}")
    public CollectionModel<EntityModel<UrlUsesPer>> countByYear(@PathVariable String shortURL) {

        List<EntityModel<UrlUsesPer>> uses = analyticsRepository.countByYear(shortURL)
                .stream()
                .map(perMonthAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(uses,
                linkTo(methodOn(AnalyticsController.class).countByYear(shortURL)).withSelfRel());

    }

    @GetMapping("/analytics/months/{shortURL}")
    public CollectionModel<EntityModel<UrlUsesPer>> countByMonth(@PathVariable String shortURL) {

        List<EntityModel<UrlUsesPer>> uses = analyticsRepository.countByMonth(shortURL)
                .stream()
                .map(perMonthAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(uses,
                linkTo(methodOn(AnalyticsController.class).countByMonth(shortURL)).withSelfRel());

    }


    @GetMapping("/analytics/days/{shortURL}")
    public CollectionModel<EntityModel<UrlUsesPer>> countByDay(@PathVariable String shortURL) {

        List<EntityModel<UrlUsesPer>> uses = analyticsRepository.countByDay(shortURL)
                .stream()
                .map(perMonthAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(uses,
                linkTo(methodOn(AnalyticsController.class).countByDay(shortURL)).withSelfRel());

    }


}
