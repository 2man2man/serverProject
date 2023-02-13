package com.thumann.server.service.article;

import com.thumann.server.domain.tenant.Tenant;

public class ArticleCreateDto extends ArticleCreateUpdateDto
{
    private Tenant tenant;

    public Tenant getTenant()
    {
        return tenant;
    }

    public void setTenant( Tenant tenant )
    {
        this.tenant = tenant;
    }

}
