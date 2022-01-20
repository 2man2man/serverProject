package com.thumann.server.service.tenant;

import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.web.controller.tenant.TenantCreateDTO;

public interface TenantService {

	Tenant createTenant(TenantCreateDTO createDTO);

	Tenant getByNumber(String number);

}