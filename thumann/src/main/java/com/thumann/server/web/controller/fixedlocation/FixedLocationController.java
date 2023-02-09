package com.thumann.server.web.controller.fixedlocation;

import java.math.BigInteger;

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
import com.thumann.server.domain.user.Employee;
import com.thumann.server.helper.collection.CollectionUtil;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.fixedlocation.FixedLocationService;
import com.thumann.server.service.helper.UserThreadHelper;
import com.thumann.server.web.controller.fixedlocation.helper.FixedLocationBatchActionAbstractHelper;
import com.thumann.server.web.exception.APIBadRequestException;
import com.thumann.server.web.exception.APIMissingAuthorizationException;
import com.thumann.server.web.helper.search.ApiSearchHelper;

@RestController
@RequestMapping( value = "/fixedLocation", consumes = "application/json", produces = "application/json" )
public class FixedLocationController
{
    @Autowired
    private BaseService                    baseService;

//    @Autowired
//    private ReponseFactory                 responseFactory;

    @Autowired
    private FixedLocationService           fixedLocationService;

    @Autowired
    private FixedLocationControllerFactory factory;

    @PostMapping( value = "/batchAction" )
    public @ResponseBody ResponseEntity<?> batchAction( @RequestBody ObjectNode json )
    {
        checkPrivilege();

        FixedLocationBatchActionDTO batchActionDto = factory.createBatchActionDTO( json );
        FixedLocationBatchActionAbstractHelper helper = FixedLocationBatchActionAbstractHelper.create( batchActionDto, fixedLocationService );
        if ( helper.getTotalCount().compareTo( BigInteger.valueOf( 10000 ) ) > 0 ) {
            throw APIBadRequestException.create( "A maximum of 1000000 locations can be created at one time" );
        }

        helper.execute();

        return ResponseEntity.status( HttpStatus.CREATED ).build();
    }

    @PostMapping( value = "/search" )
    public @ResponseBody ResponseEntity<?> search( @RequestBody ObjectNode json )
    {
        FixedLocationSearchParams searchParams = new FixedLocationSearchParams( factory );
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

}