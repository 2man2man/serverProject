package com.thumann.server.web.controller.article;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.Domain;
import com.thumann.server.domain.article.Article;
import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.helper.json.JsonUtil;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.service.article.ArticleCreateDto;
import com.thumann.server.service.article.ArticleService;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.tenant.TenantService;
import com.thumann.server.web.exception.APIEntityNotFoundException;
import com.thumann.server.web.exception.APIMissingFieldException;
import com.thumann.server.web.exception.APINumberConflictException;

public class ArticleAPICreateDto extends ArticleAPICreateUpdateDTO
{
    private long tenantId;

    @Override
    public void initValues( ObjectNode json )
    {
        super.initValues( json );
        setTenantId( JsonUtil.getLong( json, "tenantId", Domain.UNKOWN_ID ) );
    }

    public void check( BaseService baseService, ArticleService articleService )
    {
        checkRequiredFields();
        checkArticleNumber( baseService, articleService );
    }

    private void checkArticleNumber( BaseService baseService, ArticleService articleService )
    {
        Tenant tenant = baseService.getById( getTenantId(), Tenant.class );
        String number = getNumber();
        Article existingArticle = articleService.find( number, tenant );
        if ( existingArticle != null ) {
            throw APINumberConflictException.create( Article.class, "number", number );
        }
    }

    private void checkRequiredFields()
    {
        if ( StringUtil.isEmpty( getName() ) ) {
            throw APIMissingFieldException.create( "name" );
        }
        else if ( StringUtil.isEmpty( getNumber() ) ) {
            throw APIMissingFieldException.create( "number" );
        }
        else if ( getTenantId() == Domain.UNKOWN_ID ) {
            throw APIMissingFieldException.create( "tenantNumber" );
        }
    }

    public ArticleCreateDto toCreateDto( TenantService tenantService, ArticleService articleService )
    {
        ArticleCreateDto dto = new ArticleCreateDto();
        dto.setName( getName() );
        dto.setNumber( getNumber() );

        Tenant tenant = tenantService.getById( getTenantId(), true );
        if ( tenant == null ) {
            throw APIEntityNotFoundException.create( Tenant.class, "id", getTenantId() );
        }

        Article existingArticle = articleService.find( dto.getNumber(), tenant );
        if ( existingArticle != null ) {
            throw APINumberConflictException.create( Article.class, "number", dto.getNumber() );
        }

        dto.setTenant( tenant );
        return dto;
    }

    public long getTenantId()
    {
        return tenantId;
    }

    public void setTenantId( long tenantId )
    {
        this.tenantId = tenantId;
    }

}
