package com.thumann.server.web.controller.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.article.Article;
import com.thumann.server.service.article.ArticleService;
import com.thumann.server.service.tenant.TenantService;
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

}