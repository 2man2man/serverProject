package com.thumann.server.web.controller.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.article.Article;
import com.thumann.server.service.article.ArticleService;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.web.controller.tenant.TenantControllerFactory;

@Configuration
@Scope( value = ConfigurableBeanFactory.SCOPE_SINGLETON )
public class ArticleControllerFactory
{
    @Autowired
    private TenantControllerFactory tenantFactory;

    @Autowired
    private BaseService             baseService;

    @Autowired
    private ArticleService          articleService;

    public ArticleAPICreateDto createCreateDTO( ObjectNode node )
    {
        ArticleAPICreateDto dto = new ArticleAPICreateDto();
        dto.initValues( node );
        dto.check( baseService, articleService );
        return dto;
    }

    public ArticleAPIUpdateDto createUpdateDTO( ObjectNode node, Article existing )
    {
        ArticleAPIUpdateDto dto = new ArticleAPIUpdateDto();
        dto.initValues( node, existing );
        dto.check( baseService, articleService );
        return dto;
    }

    public ArticleResponseDTO createResponseDTO( Article article )
    {
        ArticleResponseDTO dto = new ArticleResponseDTO();
        dto.initValues( article, tenantFactory );
        return dto;
    }

    public ArticleResponseDTO createResponseDTO( long id )
    {
        Article domain = baseService.getById( id, Article.class );
        ArticleResponseDTO dto = new ArticleResponseDTO();
        dto.initValues( domain, tenantFactory );
        return dto;
    }

    public ArticleShortResponseDTO createShortResponseDTO( Article article )
    {
        ArticleShortResponseDTO dto = new ArticleShortResponseDTO();
        dto.initValues( article );
        return dto;
    }

}
