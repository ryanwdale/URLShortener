package com.example.URLshortener;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsesPerResourceAssembler implements RepresentationModelAssembler<UrlUsesPer, EntityModel<UrlUsesPer>> {

    @Override
    public EntityModel<UrlUsesPer> toModel(UrlUsesPer uses) {
        return EntityModel.of(uses,
                linkTo(methodOn(UrlController.class).getFullURL(uses.getShortenedURL())).withRel("url"),
                linkTo(methodOn(UrlController.class).all()).withRel("urls"));
    }

}