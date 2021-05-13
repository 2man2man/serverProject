package com.thumann.server.controller.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thumann.server.domain.article.Article;
import com.thumann.server.service.article.ArticleService;

@RestController
@RequestMapping("/test")

public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@RequestMapping("/abc/{articleName}/{articleNumber}")
	public String find(@PathVariable String articleName, @PathVariable String articleNumber)
	{

		String resultName = articleName;
		// String resultNumber = articleNumber;
		return resultName;

	}

	@PostMapping(value = "/article")
	public @ResponseBody ResponseEntity<?> signUp(@RequestBody ArticleModel article)
	{
		Article savedArticle = articleService.save(article);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);

	}

}