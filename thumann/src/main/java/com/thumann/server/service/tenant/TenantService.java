package com.thumann.server.service.tenant;

import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.web.controller.tenant.TenantCreateDto;
import com.thumann.server.web.controller.tenant.TenantUpdateDto;

public interface TenantService
{
    void createMainTenant();

    Tenant createTenant( TenantCreateDto createDTO );

    Tenant updateTenant( TenantUpdateDto dto );

    Tenant getByNumber( String number, boolean onlyCallerTenants );

}
