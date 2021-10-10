package com.thumann.server.web.controller.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.article.Article;
import com.thumann.server.service.article.ArticleService;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.tenant.TenantService;
import com.thumann.server.web.exception.APIBadRequestException;
import com.thumann.server.web.exception.APIEntityNotFoundException;
import com.thumann.server.web.response.ReponseFactory;

@RestController
@RequestMapping( value = "/article", consumes = "application/json", produces = "application/json" )
public class ArticleController
{
    @Autowired
    private ReponseFactory           responseFactory;

    @Autowired
    private ArticleService           articleService;

    @Autowired
    private BaseService              baseService;

    @Autowired
    private TenantService            tenantService;

    @Autowired
    private ArticleControllerFactory factory;

    @PostMapping
    public @ResponseBody ResponseEntity<?> createArticle( @RequestBody ObjectNode json )
    {
        ArticleAPICreateDTO createDto = factory.createCreateDTO( json );

        Article article = articleService.createArticle( createDto.toCreateDto( tenantService, articleService ) );
        ArticleResponseDTO reponseDto = factory.createResponseDTO( article );
        return ResponseEntity.status( HttpStatus.CREATED ).body( responseFactory.createResponse( reponseDto ) );
    }

    @GetMapping( value = "/getById/{id}" )
    public @ResponseBody ResponseEntity<?> getById( @PathVariable long id )
    {
        if ( id <= 0 ) {
            throw APIBadRequestException.create( "id must be greater than 1" );
        }

        Article article = baseService.getMananger().find( Article.class, id );
        if ( article == null ) {
            throw APIEntityNotFoundException.create( Article.class, "id", id );
        }
        ArticleResponseDTO reponseDto = factory.createResponseDTO( article );
        return ResponseEntity.status( HttpStatus.OK ).body( responseFactory.createResponse( reponseDto ) );
    }

}