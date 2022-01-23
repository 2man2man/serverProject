package com.thumann.server.web.controller.employee;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.Domain;
import com.thumann.server.domain.user.Employee;
import com.thumann.server.service.tenant.TenantService;
import com.thumann.server.web.exception.APIMissingFieldException;

public class EmployeeUpdateDTO extends EmployeeCreateUpdateDTO
{
    private long employeeId = Domain.UNKOWN_ID;

    @Override
    public void initValues( ObjectNode json, TenantService tenantService )
    {
        throw new IllegalAccessError( "Call initValues with existingEmployee" );
    }

    public void initValues( ObjectNode json, TenantService tenantService, Employee existingEmployee )
    {
        super.initValues( json, tenantService );
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

}