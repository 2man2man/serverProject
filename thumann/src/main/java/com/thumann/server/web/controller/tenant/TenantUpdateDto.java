package com.thumann.server.web.controller.tenant;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.Domain;
import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.service.tenant.TenantService;
import com.thumann.server.web.exception.APIMissingFieldException;
import com.thumann.server.web.exception.APINumberConflictException;

public class TenantUpdateDto extends TenantCreateUpdateDTO
{
    private long tenantId = Domain.UNKOWN_ID;

    public void check( TenantService tenantService )
    {
        checkRequiredFields();
        checkNumberConflict( tenantService );
    }

    public void initValues( ObjectNode json, Tenant existingTenant )
    {
        super.initValues( json );
        this.setTenantId( existingTenant.getId() );
    }

    public void checkRequiredFields()
    {
        if ( getTenantId() == Domain.UNKOWN_ID ) {
            throw APIMissingFieldException.create( "tenantId" );
        }
    }

    private void checkNumberConflict( TenantService tenantService )
    {
        Tenant byNumber = tenantService.getByNumber( getNumber(), false );
        if ( byNumber != null && byNumber.getId() != this.tenantId ) {
            throw APINumberConflictException.create( Tenant.class, "number", getNumber() );
        }
    }

    public long getTenantId()
    {
        return tenantId;
    }

    public void setTenantId( long tenantId )
    {
        this.tenantId = tenantId;
    }

}
