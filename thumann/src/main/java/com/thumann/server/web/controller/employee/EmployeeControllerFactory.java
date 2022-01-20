package com.thumann.server.web.controller.employee;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.user.Employee;

@Configuration
@Scope( value = ConfigurableBeanFactory.SCOPE_SINGLETON )
public class EmployeeControllerFactory
{
    public EmployeeCreateDTO createCreateDTO( ObjectNode node )
    {
        EmployeeCreateDTO dto = new EmployeeCreateDTO();
        dto.initValues( node );
        dto.checkRequiredFields();
        return dto;
    }

    public EmployeeResponseDTO createResponseDTO( Employee pupil )
    {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.initValues( pupil );
        return dto;
    }

}
