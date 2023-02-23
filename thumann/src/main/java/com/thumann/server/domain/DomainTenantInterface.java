package com.thumann.server.domain;

import com.thumann.server.domain.tenant.Tenant;

public interface DomainTenantInterface
{
    Long getId();

    Tenant getTenant();
}
