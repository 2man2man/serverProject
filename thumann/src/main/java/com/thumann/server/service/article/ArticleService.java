package com.thumann.server.service.article;

import com.thumann.server.controller.article.ArticleModel;
import com.thumann.server.domain.article.Article;

public interface ArticleService {

	Article save(ArticleModel articleModel);

}
