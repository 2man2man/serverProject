package com.thumann.server.web.controller.article;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.article.Article;
import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.dto.article.ArticleCreateDto;
import com.thumann.server.helper.json.JsonUtil;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.service.article.ArticleService;
import com.thumann.server.service.tenant.TenantService;
import com.thumann.server.web.exception.APIEntityNotFoundException;
import com.thumann.server.web.exception.APIMissingFieldException;
import com.thumann.server.web.exception.APINumberConflictException;

public class ArticleAPICreateDTO
{
    private String name;

    private String number;

    private String tenantNumber;

    public ArticleCreateDto toCreateDto( TenantService tenantService, ArticleService articleService )
    {
        ArticleCreateDto dto = new ArticleCreateDto();
        dto.setName( getName() );
        dto.setNumber( getNumber() );

        Tenant tenant = tenantService.getByNumber( getTenantNumber() );
        if ( tenant == null ) {
            throw APIEntityNotFoundException.create( Tenant.class, "number", getTenantNumber() );
        }
        Article existingArticle = articleService.find( dto.getNumber(), tenant );
        if ( existingArticle != null ) {
            throw APINumberConflictException.create( Article.class, "number", dto.getNumber() );
        }

        dto.setTenant( tenant );
        return dto;
    }

    public void initValues( ObjectNode json )
    {
        setName( JsonUtil.getString( json, "name" ) );
        setNumber( JsonUtil.getString( json, "number" ) );
        setTenantNumber( JsonUtil.getString( json, "tenantNumber" ) );
    }

    public void checkRequiredFields()
    {
        if ( StringUtil.isEmpty( getName() ) ) {
            throw APIMissingFieldException.create( "name" );
        }
        else if ( StringUtil.isEmpty( getNumber() ) ) {
            throw APIMissingFieldException.create( "number" );
        }
        else if ( StringUtil.isEmpty( getTenantNumber() ) ) {
            throw APIMissingFieldException.create( "tenantNumber" );
        }
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber( String number )
    {
        this.number = number;
    }

    public String getTenantNumber()
    {
        return tenantNumber;
    }

    public void setTenantNumber( String tenantNumber )
    {
        this.tenantNumber = tenantNumber;
    }

}
