package com.thumann.server.service.article;

import com.thumann.server.domain.article.Article;
import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.dto.article.ArticleCreateDto;

public interface ArticleService
{

    Article createArticle( ArticleCreateDto createDTO );

    Article find( String number, Tenant tenant );

}
