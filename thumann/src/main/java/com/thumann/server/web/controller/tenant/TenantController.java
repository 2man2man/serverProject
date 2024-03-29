package com.thumann.server.web.controller.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.thumann.server.web.exception.APIBadRequestException;
import com.thumann.server.web.exception.APIEntityNotFoundException;
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

        TenantCreateDto createDto = factory.createCreateDTO( json, tenantService );
        Tenant tenant = tenantService.createTenant( createDto );
        TenantResponseDTO reponseDto = factory.createResponseDTO( tenant.getId() );
        return ResponseEntity.status( HttpStatus.CREATED ).body( responseFactory.createResponse( reponseDto ) );
    }

    @PutMapping( value = "/updateById/{id}" )
    public @ResponseBody ResponseEntity<?> updateTenant( @PathVariable long id, @RequestBody ObjectNode json )
    {
        checkPrivilege();
        Tenant existingTenant = findById( id );

        TenantUpdateDto updateDto = factory.createUpdateDTO( existingTenant, json, tenantService );
        Tenant tenant = tenantService.updateTenant( updateDto );
        TenantResponseDTO reponseDto = factory.createResponseDTO( tenant.getId() );
        return ResponseEntity.status( HttpStatus.OK ).body( responseFactory.createResponse( reponseDto ) );
    }

    @GetMapping( value = "/getByNumber/{number}" )
    public @ResponseBody ResponseEntity<?> getByNumber( @PathVariable String number )
    {
        Tenant domain = tenantService.getByNumber( number, true );

        if ( domain == null ) {
            throw APIEntityNotFoundException.create( Tenant.class, "number", number );
        }

        TenantResponseDTO reponseDto = factory.createResponseDTO( domain.getId() );
        return ResponseEntity.status( HttpStatus.OK ).body( responseFactory.createResponse( reponseDto ) );
    }

    @GetMapping( value = "/getById/{id}" )
    public @ResponseBody ResponseEntity<?> getById( @PathVariable long id )
    {
        Tenant domain = findById( id );
        TenantResponseDTO reponseDto = factory.createResponseDTO( domain.getId() );
        return ResponseEntity.status( HttpStatus.OK ).body( responseFactory.createResponse( reponseDto ) );
    }

    @PostMapping( value = "/search" )
    public @ResponseBody ResponseEntity<?> search( @RequestBody ObjectNode json )
    {
        TenantSearchParams searchParams = new TenantSearchParams( factory );
        searchParams.initParams( json );

        ApiSearchHelper searchHelper = new ApiSearchHelper( baseService );
        JsonNode result = searchHelper.executeSearch( searchParams );

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    private Tenant findById( long id )
    {
        Tenant domain = baseService.getById( id, Tenant.class );
        if ( domain == null ) {
            throw APIEntityNotFoundException.create( Tenant.class, "id", id );
        }
        else if ( !baseService.getCallerTenantIds().contains( domain.getId() ) ) {
            throw APIBadRequestException.create( "Tenant with id [" + id + "] can not be accessed!" );
        }
        return domain;
    }

    private void checkPrivilege()
    {
        Employee caller = baseService.getById( UserThreadHelper.getUser(), Employee.class, CollectionUtil.setOf( "privilege" ) );
        if ( !caller.getPrivilege().isSystemConfiguration() ) {
            throw APIMissingAuthorizationException.create( "systemConfiguration" );
        }
    }

}