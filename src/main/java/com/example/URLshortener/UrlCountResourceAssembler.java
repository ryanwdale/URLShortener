package com.example.URLshortener;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UrlCountResourceAssembler implements RepresentationModelAssembler<UrlCount, EntityModel<UrlCount>> {

    @Override
    public EntityModel<UrlCount> toModel(UrlCount count) {
        return EntityModel.of(count,
                linkTo(methodOn(AnalyticsController.class)
                        .countBetweenDates(count.getShortenedURL(), count.getStartDate(), count.getEndDate()))
                        .withSelfRel(),
                linkTo(methodOn(UrlController.class).getFullURL(count.getShortenedURL())).withRel("url"),
                linkTo(methodOn(UrlController.class).all()).withRel("urls"));
    }

}
