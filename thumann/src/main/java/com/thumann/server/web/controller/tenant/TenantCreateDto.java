package com.thumann.server.web.controller.tenant;

import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.service.tenant.TenantService;
import com.thumann.server.web.exception.APIMissingFieldException;
import com.thumann.server.web.exception.APINumberConflictException;

public class TenantCreateDto extends TenantCreateUpdateDTO
{
    public void check( TenantService tenantService )
    {
        checkRequiredFields();
        checkNumberConflict( tenantService );
    }

    private void checkRequiredFields()
    {
        if ( StringUtil.isEmpty( getName() ) ) {
            throw APIMissingFieldException.create( "name" );
        }
        else if ( StringUtil.isEmpty( getNumber() ) ) {
            throw APIMissingFieldException.create( "number" );
        }
    }

    private void checkNumberConflict( TenantService tenantService )
    {
        Tenant byNumber = tenantService.getByNumber( getNumber(), false );
        if ( byNumber != null ) {
            throw APINumberConflictException.create( Tenant.class, "number", getNumber() );
        }
    }
}
