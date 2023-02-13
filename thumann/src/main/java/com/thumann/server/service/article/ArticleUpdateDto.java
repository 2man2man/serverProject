package com.thumann.server.service.article;

public class ArticleUpdateDto extends ArticleCreateUpdateDto
{
    private long existingArticleId;

    public long getExistingArticleId()
    {
        return existingArticleId;
    }

    public void setExistingArticleId( long existingArticleId )
    {
        this.existingArticleId = existingArticleId;
    }

}
