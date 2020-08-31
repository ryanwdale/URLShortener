package com.example.URLshortener;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsesPerMonthResourceAssembler implements RepresentationModelAssembler<UrlUsesPerMonth, EntityModel<UrlUsesPerMonth>> {

    @Override
    public EntityModel<UrlUsesPerMonth> toModel(UrlUsesPerMonth uses) {
        return EntityModel.of(uses,
                linkTo(methodOn(MainController.class).getFullURL(uses.getShortenedURL())).withRel("url"),
                linkTo(methodOn(MainController.class).all()).withRel("urls"));
    }

}