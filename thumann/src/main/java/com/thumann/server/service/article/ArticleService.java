package com.thumann.server.service.article;

import com.thumann.server.domain.article.Article;
import com.thumann.server.domain.tenant.Tenant;

public interface ArticleService
{
    Article find( String number, Tenant tenant );

    Article create( ArticleCreateDto createDTO );

    Article update( ArticleUpdateDto updateDTO );

}
