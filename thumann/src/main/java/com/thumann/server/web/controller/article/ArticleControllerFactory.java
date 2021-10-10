package com.thumann.server.web.controller.article;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.article.Article;

@Configuration
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ArticleControllerFactory {

	public ArticleAPICreateDTO createCreateDTO(ObjectNode node)
	{
		ArticleAPICreateDTO dto = new ArticleAPICreateDTO();
		dto.initValues(node);
		dto.checkRequiredFields();
		return dto;
	}

	public ArticleResponseDTO createResponseDTO(Article article)
	{
		ArticleResponseDTO dto = new ArticleResponseDTO();
		dto.initValues(article);
		return dto;
	}

}
