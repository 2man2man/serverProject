package com.thumann.server.web.controller.employee;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.helper.json.JsonUtil;
import com.thumann.server.service.tenant.TenantService;
import com.thumann.server.web.exception.APIEntityNotFoundException;

public class EmployeeCreateUpdateDTO
{
    private String      firstName;

    private String      lastName;

    private String      userName;

    private String      password;

    private Date        dateOfBirth;

    private Boolean     systemConfigurationPrivilege;

    private Boolean     articleModifyPrivilege;

    private Set<Tenant> tenants = new HashSet<>();

    public void initValues( ObjectNode json, TenantService tenantService )
    {
        setFirstName( JsonUtil.getString( json, "firstName" ) );
        setLastName( JsonUtil.getString( json, "lastName" ) );
        setUserName( JsonUtil.getString( json, "userName" ) );
        setPassword( JsonUtil.getString( json, "password" ) );
        setDateOfBirth( JsonUtil.getDate( json, "dateOfBirth" ) );
        setSystemConfigurationPrivilege( JsonUtil.getBoolean( json, "systemConfigurationPrivilege", null ) );
        setSystemConfigurationPrivilege( JsonUtil.getBoolean( json, "articleModifyPrivilege", null ) );

        Set<String> tenantNumbers = new HashSet<String>( JsonUtil.getStringArray( json, "tenants" ) );
        for ( String tenantNumber : tenantNumbers ) {
            Tenant tenant = tenantService.getByNumber( tenantNumber, false );
            if ( tenant == null ) {
                throw APIEntityNotFoundException.create( Tenant.class, "number", tenantNumber );
            }
            tenants.add( tenant );
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

    public Boolean isSystemConfigurationPrivilege()
    {
        return systemConfigurationPrivilege;
    }

    public void setSystemConfigurationPrivilege( Boolean systemConfigurationPrivilege )
    {
        this.systemConfigurationPrivilege = systemConfigurationPrivilege;
    }

    public Boolean getArticleModifyPrivilege()
    {
        return articleModifyPrivilege;
    }

    public void setArticleModifyPrivilege( Boolean articleModifyPrivilege )
    {
        this.articleModifyPrivilege = articleModifyPrivilege;
    }

}
