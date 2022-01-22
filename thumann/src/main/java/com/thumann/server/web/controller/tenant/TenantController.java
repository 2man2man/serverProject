package com.thumann.server.web.controller.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.domain.user.Employee;
import com.thumann.server.helper.collection.CollectionUtil;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.helper.UserThreadHelper;
import com.thumann.server.service.tenant.TenantService;
import com.thumann.server.web.exception.APIMissingAuthorizationException;
import com.thumann.server.web.helper.search.ApiSearchHelper;
import com.thumann.server.web.response.ReponseFactory;

@RestController
@RequestMapping( value = "/tenant", consumes = "application/json", produces = "application/json" )
public class TenantController
{
    @Autowired
    private BaseService             baseService;

    @Autowired
    private ReponseFactory          responseFactory;

    @Autowired
    private TenantService           tenantService;

    @Autowired
    private TenantControllerFactory factory;

    @PostMapping
    public @ResponseBody ResponseEntity<?> createTenant( @RequestBody ObjectNode json )
    {
        checkPrivilege();

        TenantCreateDTO createDto = factory.createCreateDTO( json, tenantService );
        Tenant tenant = tenantService.createTenant( createDto );
        TenantResponseDTO reponseDto = factory.createResponseDTO( tenant.getId() );
        return ResponseEntity.status( HttpStatus.CREATED ).body( responseFactory.createResponse( reponseDto ) );
    }

    @PostMapping( value = "/search" )
    public @ResponseBody ResponseEntity<?> search( @RequestBody ObjectNode json )
    {
        checkPrivilege();

        TenantSearchParams searchParams = new TenantSearchParams( factory );
        searchParams.initParams( json );

        ApiSearchHelper searchHelper = new ApiSearchHelper( baseService );
        JsonNode result = searchHelper.executeSearch( searchParams );

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    private void checkPrivilege()
    {
        Employee caller = baseService.getById( UserThreadHelper.getUser(), Employee.class, CollectionUtil.setOf( "privilege" ) );
        if ( !caller.getPrivilege().isSystemConfiguration() ) {
            throw APIMissingAuthorizationException.create( "systemConfiguration" );
        }
    }

}