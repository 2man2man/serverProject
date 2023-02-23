package com.thumann.server.web.controller.employee;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.Domain;
import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.domain.warehouse.Warehouse;
import com.thumann.server.helper.json.JsonUtil;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.tenant.TenantService;
import com.thumann.server.web.exception.APIEntityNotFoundException;

public class EmployeeCreateUpdateDTO
{
    private String      firstName;

    private String      lastName;

    private String      userName;

    private String      password;

    private Boolean     systemConfigurationPrivilege;

    private Boolean     logisticConfigurationPrivilege;

    private Set<Tenant> tenants = new HashSet<>();

    private Warehouse   warehouse;

    public void initValues( ObjectNode json, BaseService baseService, TenantService tenantService )
    {
        setFirstName( JsonUtil.getString( json, "firstName" ) );
        setLastName( JsonUtil.getString( json, "lastName" ) );
        setUserName( JsonUtil.getString( json, "userName" ) );
        setPassword( JsonUtil.getString( json, "password" ) );
        setSystemConfigurationPrivilege( JsonUtil.getBoolean( json, "systemConfigurationPrivilege", null ) );
        setLogisticConfigurationPrivilege( JsonUtil.getBoolean( json, "logisticConfigurationPrivilege", null ) );

        Set<String> tenantNumbers = new HashSet<String>( JsonUtil.getStringArray( json, "tenants" ) );
        for ( String tenantNumber : tenantNumbers ) {
            Tenant tenant = tenantService.getByNumber( tenantNumber, false );
            if ( tenant == null ) {
                throw APIEntityNotFoundException.create( Tenant.class, "number", tenantNumber );
            }
            tenants.add( tenant );
        }

        long warehouseId = JsonUtil.getLong( json, "warehouseId", Domain.UNKOWN_ID );
        if ( warehouseId != Domain.UNKOWN_ID ) {
            Warehouse wh = baseService.getById( warehouseId, Warehouse.class );
            if ( wh == null ) {
                throw APIEntityNotFoundException.create( Warehouse.class, "id", warehouseId );
            }
            this.warehouse = wh;
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

    public Boolean getLogisticConfigurationPrivilege()
    {
        return logisticConfigurationPrivilege;
    }

    public void setLogisticConfigurationPrivilege( Boolean logisticConfigurationPrivilege )
    {
        this.logisticConfigurationPrivilege = logisticConfigurationPrivilege;
    }

    public Warehouse getWarehouse()
    {
        return warehouse;
    }

    public void setWarehouse( Warehouse warehouse )
    {
        this.warehouse = warehouse;
    }

}
