package com.thumann.server.web.controller.warehouse.area;

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
import com.thumann.server.domain.user.Employee;
import com.thumann.server.domain.warehouse.area.WarehouseArea;
import com.thumann.server.helper.collection.CollectionUtil;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.helper.UserThreadHelper;
import com.thumann.server.service.warehouse.WarehouseService;
import com.thumann.server.web.exception.APIEntityNotFoundException;
import com.thumann.server.web.exception.APIMissingAuthorizationException;
import com.thumann.server.web.helper.search.ApiSearchHelper;
import com.thumann.server.web.response.ReponseFactory;

@RestController
@RequestMapping( value = "/warehouse/area", consumes = "application/json", produces = "application/json" )
public class WarehouseAreaController
{
    @Autowired
    private BaseService                    baseService;

    @Autowired
    private ReponseFactory                 responseFactory;

    @Autowired
    private WarehouseService               warehouseService;

    @Autowired
    private WarehouseAreaControllerFactory factory;

    @PostMapping( )
    public @ResponseBody ResponseEntity<?> create( @RequestBody ObjectNode json )
    {
        checkPrivilege();
        WarehouseAreaCreateDTO createDto = factory.createCreateDTO( json );

        WarehouseArea domain = warehouseService.create( createDto );
        WarehouseAreaResponseDTO reponseDto = factory.createResponseDTO( domain.getId() );
        return ResponseEntity.status( HttpStatus.CREATED ).body( responseFactory.createResponse( reponseDto ) );
    }

    @PutMapping( value = "/updateById/{id}" )
    public @ResponseBody ResponseEntity<?> updateById( @PathVariable long id, @RequestBody ObjectNode json )
    {
        checkPrivilege();

        WarehouseArea domain = findById( id );

        WarehouseAreaUpdateDTO updateDto = factory.createUpdateDTO( domain, json );
        domain = warehouseService.update( updateDto );
        WarehouseAreaResponseDTO reponseDto = factory.createResponseDTO( domain.getId() );
        return ResponseEntity.status( HttpStatus.OK ).body( responseFactory.createResponse( reponseDto ) );
    }

    @GetMapping( value = "/getById/{id}" )
    public @ResponseBody ResponseEntity<?> getById( @PathVariable long id )
    {
        WarehouseArea domain = findById( id );
        WarehouseAreaResponseDTO reponseDto = factory.createResponseDTO( domain.getId() );
        return ResponseEntity.status( HttpStatus.OK ).body( responseFactory.createResponse( reponseDto ) );
    }

    @PostMapping( value = "/search" )
    public @ResponseBody ResponseEntity<?> search( @RequestBody ObjectNode json )
    {
        WarehouseAreaSearchParams searchParams = new WarehouseAreaSearchParams( factory );
        searchParams.initParams( json );

        ApiSearchHelper searchHelper = new ApiSearchHelper( baseService );
        JsonNode result = searchHelper.executeSearch( searchParams );

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    private void checkPrivilege()
    {
        Employee caller = baseService.getById( UserThreadHelper.getUser(), Employee.class, CollectionUtil.setOf( "privilege" ) );
        if ( !caller.getPrivilege().isLogisticConfiguration() ) {
            throw APIMissingAuthorizationException.create( "logisticConfiguration" );
        }
    }

    private WarehouseArea findById( long id )
    {
        WarehouseArea domain = baseService.getById( id, WarehouseArea.class );
        if ( domain == null ) {
            throw APIEntityNotFoundException.create( WarehouseArea.class, "id", id );
        }
        return domain;
    }

}