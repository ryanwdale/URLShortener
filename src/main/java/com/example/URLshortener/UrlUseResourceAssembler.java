package com.example.URLshortener;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class UrlUseResourceAssembler implements RepresentationModelAssembler<UrlUse, EntityModel<UrlUse>> {

    @Override
    public EntityModel<UrlUse> toModel(UrlUse url) {
        return EntityModel.of(url);
    }

}