package com.example.URLshortener;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class UrlResourceAssembler implements RepresentationModelAssembler<ShortenedURL, EntityModel<ShortenedURL>> {

    @Override
    public EntityModel<ShortenedURL> toModel(ShortenedURL url) {
        return EntityModel.of(url,
                linkTo(methodOn(MainController.class).getFullURL(url.getShortenedURL())).withSelfRel());
    }

}
