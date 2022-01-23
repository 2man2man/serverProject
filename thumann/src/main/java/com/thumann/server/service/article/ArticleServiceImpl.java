package com.thumann.server.service.article;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thumann.server.domain.Domain;
import com.thumann.server.domain.article.Article;
import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.dto.article.ArticleCreateDto;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.helper.UserThreadHelper;

@Service( "articleService" )
@Scope( value = ConfigurableBeanFactory.SCOPE_SINGLETON )
@Transactional
public class ArticleServiceImpl implements ArticleService
{
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BaseService   baseService;

    public ArticleServiceImpl()
    {
    }

    @Override
    public Article createArticle( ArticleCreateDto createDTO )
    {
        if ( createDTO == null ) {
            throw new IllegalArgumentException( "createDTO must not be null" );
        }
        else if ( StringUtil.isEmpty( createDTO.getNumber() ) ) {
            throw new IllegalArgumentException( "number must not be null" );
        }
        else if ( StringUtil.isEmpty( createDTO.getName() ) ) {
            throw new IllegalArgumentException( "name must not be null" );
        }
        else if ( createDTO.getTenant() == null ) {
            throw new IllegalArgumentException( "tenant must not be null" );
        }
        else if ( !baseService.getCallerTenantIds().contains( createDTO.getTenant().getId() ) ) {
            throw new IllegalArgumentException( "Tenant with id :" + createDTO.getTenant().getId() + " not allowed for user with id: " + UserThreadHelper.getUser() );
        }

        Article existingArticle = find( createDTO.getNumber(), createDTO.getTenant() );
        if ( existingArticle != null ) {
            throw new IllegalArgumentException( "there already exists an article with number " + createDTO.getNumber() + " for tenant "
                + entityManager.find( Tenant.class, createDTO.getTenant().getId() ).getNumber() );
        }

        Article article = new Article();
        article.setNumber( createDTO.getNumber() );
        article.setName( createDTO.getName() );
        article.setTenant( createDTO.getTenant() );

        return entityManager.merge( article );
    }

    @Override
    public Article find( String number, Tenant tenant )
    {
        if ( StringUtil.isEmpty( number ) ) {
            throw new IllegalArgumentException( "number must not be null" );
        }
        else if ( tenant == null ) {
            throw new IllegalArgumentException( "tenant must not be null" );
        }
        else if ( tenant.getId() == Domain.UNKOWN_ID ) {
            throw new IllegalArgumentException( "tenant must be persisted" );
        }
        tenant = entityManager.find( Tenant.class, tenant.getId() );

        StringBuilder sb = new StringBuilder();
        sb.append( "SELECT domain " );
        sb.append( "FROM Article domain " );
        sb.append( "WHERE domain.number =:number " );
        sb.append( "AND   domain.tenant.id =:tenantId " );

        TypedQuery<Article> query = entityManager.createQuery( sb.toString(), Article.class );
        query.setParameter( "number", number );
        query.setParameter( "tenantId", tenant.getId() );

        List<Article> result = query.getResultList();
        if ( result.isEmpty() ) {
            return null;
        }
        else if ( result.size() == 1 ) {
            return result.get( 0 );
        }
        else {
            throw new IllegalStateException( "More than one article with number " + number + " for tenant " + tenant.getNumber() );
        }
    }

}