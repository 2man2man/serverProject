package com.thumann.server.startup;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thumann.server.service.tenant.TenantService;
import com.thumann.server.service.user.EmployeeService;

@Component
public class StartUpInitData implements InitializingBean
{
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TenantService   tenantService;

    @Override
    public void afterPropertiesSet() throws Exception
    {
        tenantService.createMainTenant();
        employeeService.createAdmin();
    }
}
