package com.thumann.server.web.controller.employee;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.Domain;
import com.thumann.server.domain.user.Employee;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.tenant.TenantService;
import com.thumann.server.web.exception.APIMissingFieldException;

public class EmployeeUpdateDTO extends EmployeeCreateUpdateDTO
{
    private long    employeeId       = Domain.UNKOWN_ID;

    private boolean isTenantCreation = false;

    @Override
    public void initValues( ObjectNode json, BaseService baseService, TenantService tenantService )
    {
        throw new IllegalAccessError( "Call initValues with existingEmployee" );
    }

    public void initValues( ObjectNode json, BaseService baseService, TenantService tenantService, Employee existingEmployee )
    {
        super.initValues( json, baseService, tenantService );
        this.setEmployeeId( existingEmployee.getId() );

    }

    public void checkRequiredFields()
    {
        if ( getEmployeeId() == Domain.UNKOWN_ID ) {
            throw APIMissingFieldException.create( "employeeId" );
        }
    }

    public long getEmployeeId()
    {
        return employeeId;
    }

    public void setEmployeeId( long employeeId )
    {
        this.employeeId = employeeId;
    }

    public boolean isTenantCreation()
    {
        return isTenantCreation;
    }

    public void setTenantCreation( boolean isTenantCreation )
    {
        this.isTenantCreation = isTenantCreation;
    }

}
