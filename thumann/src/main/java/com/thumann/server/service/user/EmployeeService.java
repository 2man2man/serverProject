package com.thumann.server.service.user;

import com.thumann.server.domain.user.Employee;
import com.thumann.server.web.controller.employee.EmployeeCreateDTO;

public interface EmployeeService
{
    Employee createEmployee( EmployeeCreateDTO createDTO );

    Employee getByUsername( String username );

    void createAdmin();

}
