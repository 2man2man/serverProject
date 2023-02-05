package com.thumann.server.web.controller.warehouse;

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
import com.thumann.server.domain.warehouse.Warehouse;
import com.thumann.server.helper.collection.CollectionUtil;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.helper.UserThreadHelper;
import com.thumann.server.service.warehouse.WarehouseService;
import com.thumann.server.web.exception.APIEntityNotFoundException;
import com.thumann.server.web.exception.APIMissingAuthorizationException;
import com.thumann.server.web.helper.search.ApiSearchHelper;
import com.thumann.server.web.response.ReponseFactory;

@RestController
@RequestMapping( value = "/warehouse", consumes = "application/json", produces = "application/json" )
public class WarehouseController
{
    @Autowired
    private BaseService                baseService;

    @Autowired
    private ReponseFactory             responseFactory;

    @Autowired
    private WarehouseService           warehouseService;

    @Autowired
    private WarehouseControllerFactory factory;

    @PostMapping( value = "/initNew" )
    public @ResponseBody ResponseEntity<?> initNewWarehouse()
    {
        checkPrivilege();

        Warehouse domain = warehouseService.initNewWarehouse();
        WarehouseResponseDTO reponseDto = factory.createResponseDTO( domain.getId() );
        return ResponseEntity.status( HttpStatus.CREATED ).body( responseFactory.createResponse( reponseDto ) );
    }

    @PutMapping( value = "/updateById/{id}" )
    public @ResponseBody ResponseEntity<?> updateById( @PathVariable long id, @RequestBody ObjectNode json )
    {
        checkPrivilege();

        Warehouse domain = findById( id );

        WarehouseAPIUpdateDTO updateDto = factory.createUpdateDTO( domain, json, warehouseService );
        domain = warehouseService.update( updateDto.toUpdateDto() );
        WarehouseResponseDTO reponseDto = factory.createResponseDTO( domain.getId() );
        return ResponseEntity.status( HttpStatus.OK ).body( responseFactory.createResponse( reponseDto ) );
    }

    @GetMapping( value = "/getById/{id}" )
    public @ResponseBody ResponseEntity<?> getById( @PathVariable long id )
    {
        Warehouse domain = findById( id );
        WarehouseResponseDTO reponseDto = factory.createResponseDTO( domain.getId() );
        return ResponseEntity.status( HttpStatus.OK ).body( responseFactory.createResponse( reponseDto ) );
    }

    @GetMapping( value = "/getByNumber/{number}" )
    public @ResponseBody ResponseEntity<?> getByNumber( @PathVariable String number )
    {
        Warehouse domain = findByNumber( number );
        WarehouseResponseDTO reponseDto = factory.createResponseDTO( domain.getId() );
        return ResponseEntity.status( HttpStatus.OK ).body( responseFactory.createResponse( reponseDto ) );
    }

    @PostMapping( value = "/search" )
    public @ResponseBody ResponseEntity<?> search( @RequestBody ObjectNode json )
    {
        WarehouseSearchParams searchParams = new WarehouseSearchParams( factory );
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

    private Warehouse findById( long id )
    {
        Warehouse domain = baseService.getById( id, Warehouse.class );
        if ( domain == null ) {
            throw APIEntityNotFoundException.create( Warehouse.class, "id", id );
        }
        return domain;
    }

    private Warehouse findByNumber( String number )
    {
        Warehouse domain = warehouseService.getByNumber( number );
        if ( domain == null ) {
            throw APIEntityNotFoundException.create( Warehouse.class, "number", number );
        }
        return domain;
    }

}