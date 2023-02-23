package com.thumann.server.web.controller.employee;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.user.Employee;
import com.thumann.server.helper.collection.CollectionUtil;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.tenant.TenantService;
import com.thumann.server.web.controller.warehouse.WarehouseControllerFactory;

@Configuration
@Scope( value = ConfigurableBeanFactory.SCOPE_SINGLETON )
public class EmployeeControllerFactory
{
    @Autowired
    private BaseService                baseService;

    @Autowired
    private WarehouseControllerFactory warehouseFactory;

    public EmployeeCreateDTO createCreateDTO( ObjectNode node, TenantService tenantService )
    {
        EmployeeCreateDTO dto = new EmployeeCreateDTO();
        dto.initValues( node, baseService, tenantService );
        dto.checkRequiredFields();
        return dto;
    }

    public EmployeeUpdateDTO createUpdateDTO( ObjectNode node, TenantService tenantService, Employee employee )
    {
        EmployeeUpdateDTO dto = new EmployeeUpdateDTO();
        dto.initValues( node, baseService, tenantService, employee );
        dto.checkRequiredFields();
        return dto;
    }

    public EmployeeResponseDTO createResponseDTO( long employeeId )
    {
        Employee employee = baseService.getById( employeeId, Employee.class, getEagerProps() );

        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.initValues( employee, warehouseFactory );
        return dto;
    }

    private Set<String> getEagerProps()
    {
        return CollectionUtil.setOf( "privilege", "credentials" );
    }

}
