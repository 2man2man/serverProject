package com.thumann.server.service.user;

import com.thumann.server.domain.user.Employee;
import com.thumann.server.web.controller.employee.EmployeeCreateDTO;
import com.thumann.server.web.controller.employee.EmployeeCreateUpdateDTO;
import com.thumann.server.web.controller.employee.EmployeeUpdateDTO;

public interface EmployeeService
{
    Employee createEmployee( EmployeeCreateDTO createDTO );

    Employee updateEmployee( EmployeeUpdateDTO updateDto );

    Employee getByUsername( String username );

    void createAdmin();

    String checkTenants( EmployeeCreateUpdateDTO dto );

    boolean isAdmin( long employeeId );

}
