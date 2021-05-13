package com.thumann.server.service.article;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thumann.server.controller.article.ArticleModel;
import com.thumann.server.domain.article.Article;

@Component("articleService")
@Transactional
public class ArticleServiceImpl implements ArticleService {

	@PersistenceContext //
	private EntityManager entityManager;

	public ArticleServiceImpl()
	{
	}

	@Override
	public Article save(ArticleModel articleModel)
	{
		Article article = new Article();

		String articleNumber = articleModel.getNumber();
		article.setNumber(articleNumber);

		String articleName = articleModel.getName();
		article.setName(articleName);

		Article articleReturn = entityManager.merge(article);
		return articleReturn;
	}
}
