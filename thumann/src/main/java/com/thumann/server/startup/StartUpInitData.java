package com.thumann.server.startup;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thumann.server.service.tenant.TenantService;
import com.thumann.server.service.user.EmployeeService;
import com.thumann.server.service.warehouse.WarehouseService;

@Component
public class StartUpInitData implements InitializingBean
{
    @Autowired
    private EmployeeService  employeeService;

    @Autowired
    private TenantService    tenantService;

    @Autowired
    private WarehouseService warehouseService;

    @Override
    public void afterPropertiesSet() throws Exception
    {
        tenantService.createMainTenant();
        warehouseService.createMainWarehouse();
        employeeService.createAdmin();
    }
}
