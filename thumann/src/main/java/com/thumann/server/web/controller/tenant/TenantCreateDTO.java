package com.thumann.server.web.controller.tenant;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.helper.json.JsonUtil;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.service.tenant.TenantService;
import com.thumann.server.web.exception.APIMissingFieldException;
import com.thumann.server.web.exception.APINumberConflictException;

public class TenantCreateDTO
{
    private String name;

    private String number;

    public void initValues( ObjectNode json )
    {
        setName( JsonUtil.getString( json, "name" ) );
        setNumber( JsonUtil.getString( json, "number" ) );
    }

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
        Tenant byNumber = tenantService.getByNumber( getNumber() );
        if ( byNumber != null ) {
            throw APINumberConflictException.create( Tenant.class, "number", getNumber() );
        }
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber( String number )
    {
        this.number = number;
    }

}
