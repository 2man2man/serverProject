package com.thumann.server.web.controller.employee;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.helper.json.JsonUtil;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.service.tenant.TenantService;
import com.thumann.server.web.exception.APIEntityNotFoundException;
import com.thumann.server.web.exception.APIMissingFieldException;

public class EmployeeCreateDTO
{
    private String      firstName;

    private String      lastName;

    private String      userName;

    private String      password;

    private Date        dateOfBirth;

    private boolean     systemConfigurationPrivilege = true;

    private Set<Tenant> tenants                      = new HashSet<>();

    public void initValues( ObjectNode json, TenantService tenantService )
    {
        setFirstName( JsonUtil.getString( json, "firstName" ) );
        setLastName( JsonUtil.getString( json, "lastName" ) );
        setUserName( JsonUtil.getString( json, "userName" ) );
        setPassword( JsonUtil.getString( json, "password" ) );
        setDateOfBirth( JsonUtil.getDate( json, "dateOfBirth" ) );
        setSystemConfigurationPrivilege( JsonUtil.getBoolean( json, "systemConfigurationPrivilege", true ) );

        Set<String> tenantNumbers = new HashSet<String>( JsonUtil.getStringArray( json, "tenants" ) );
        for ( String tenantNumber : tenantNumbers ) {
            Tenant tenant = tenantService.getByNumber( tenantNumber );
            if ( tenant == null ) {
                throw APIEntityNotFoundException.create( Tenant.class, "number", tenantNumber );
            }
            tenants.add( tenant );
        }
    }

    public void checkRequiredFields()
    {
        if ( StringUtil.isEmpty( getFirstName() ) ) {
            throw APIMissingFieldException.create( "firstName" );
        }
        else if ( StringUtil.isEmpty( getLastName() ) ) {
            throw APIMissingFieldException.create( "lastName" );
        }
        else if ( StringUtil.isEmpty( getUserName() ) ) {
            throw APIMissingFieldException.create( "userName" );
        }
        else if ( StringUtil.isEmpty( getPassword() ) ) {
            throw APIMissingFieldException.create( "password" );
        }
        else if ( getTenants().isEmpty() ) {
            throw APIMissingFieldException.create( "tenants" );
        }

    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth( Date dateOfBirth )
    {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName( String userName )
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public Set<Tenant> getTenants()
    {
        return tenants;
    }

    public boolean isSystemConfigurationPrivilege()
    {
        return systemConfigurationPrivilege;
    }

    public void setSystemConfigurationPrivilege( boolean systemConfigurationPrivilege )
    {
        this.systemConfigurationPrivilege = systemConfigurationPrivilege;
    }

}
