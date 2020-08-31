package com.example.URLshortener;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class UrlResourceAssembler implements RepresentationModelAssembler<ShortUrl, EntityModel<ShortUrl>> {

    @Override
    public EntityModel<ShortUrl> toModel(ShortUrl url) {
        return EntityModel.of(url,
                linkTo(methodOn(UrlController.class).getFullURL(url.getShortUrl())).withSelfRel(),
                linkTo(methodOn(UrlController.class).all()).withRel("urls"));
    }

}
