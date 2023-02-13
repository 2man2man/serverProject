package com.thumann.server.web.controller.article;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.Domain;
import com.thumann.server.domain.article.Article;
import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.service.article.ArticleService;
import com.thumann.server.service.article.ArticleUpdateDto;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.web.exception.APIMissingFieldException;
import com.thumann.server.web.exception.APINumberConflictException;

public class ArticleAPIUpdateDto extends ArticleAPICreateUpdateDTO
{
    private long articleId = Domain.UNKOWN_ID;

    public void check( BaseService baseService, ArticleService articleService )
    {
        checkRequiredFields();
        checkArticleNumber( baseService, articleService );
    }

    public void initValues( ObjectNode json, Article existingArticle )
    {
        super.initValues( json );
        this.setArticleId( existingArticle.getId() );
    }

    private void checkRequiredFields()
    {
        if ( getArticleId() == Domain.UNKOWN_ID ) {
            throw APIMissingFieldException.create( "articleId" );
        }
    }

    private void checkArticleNumber( BaseService baseService, ArticleService articleService )
    {
        if ( StringUtil.isEmpty( getNumber() ) ) {
            return;
        }
        Article art = baseService.getById( getArticleId(), Article.class );
        Tenant tenant = baseService.getById( art.getTenant().getId(), Tenant.class );
        String number = getNumber();
        Article existingArticle = articleService.find( number, tenant );
        if ( existingArticle != null && existingArticle.getId() != art.getId() ) {
            throw APINumberConflictException.create( Article.class, "number", number );
        }
    }

    public ArticleUpdateDto toUpdateDto()
    {
        ArticleUpdateDto dto = new ArticleUpdateDto();
        dto.setExistingArticleId( getArticleId() );
        dto.setName( getName() );
        dto.setNumber( getNumber() );
        return dto;
    }

    public long getArticleId()
    {
        return articleId;
    }

    public void setArticleId( long articleId )
    {
        this.articleId = articleId;
    }

}
