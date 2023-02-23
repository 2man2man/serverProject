package com.thumann.server.web.controller.stock.fixedlocation;

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
import com.thumann.server.service.base.BaseService;
import com.thumann.server.web.helper.search.ApiSearchHelper;

@RestController
@RequestMapping( value = "/stockFixedLocation", consumes = "application/json", produces = "application/json" )
public class StockFixedLocationController
{
//    @Autowired
//    private ReponseFactory                      responseFactory;

    @Autowired
    private BaseService                         baseService;

    @Autowired
    private StockFixedLocationControllerFactory factory;

    @PostMapping( value = "/search" )
    public @ResponseBody ResponseEntity<?> search( @RequestBody ObjectNode json )
    {
        StockFixedLocationSearchParams searchParams = new StockFixedLocationSearchParams( factory );
        searchParams.initParams( json );

        ApiSearchHelper searchHelper = new ApiSearchHelper( baseService );
        JsonNode result = searchHelper.executeSearch( searchParams );

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

}