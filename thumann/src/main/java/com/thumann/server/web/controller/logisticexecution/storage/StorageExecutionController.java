package com.thumann.server.web.controller.logisticexecution.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.logisticexecution.storage.StorageExecution;
import com.thumann.server.domain.user.Person;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.logisticexecution.storage.StorageService;
import com.thumann.server.web.exception.APIBadRequestException;
import com.thumann.server.web.exception.APIEntityNotFoundException;
import com.thumann.server.web.exception.APIMissingAuthorizationException;
import com.thumann.server.web.response.ReponseFactory;

//TODO: continue here
@RestController
@RequestMapping( value = "/storageExecution", consumes = "application/json", produces = "application/json" )
public class StorageExecutionController
{
    @Autowired
    private ReponseFactory                    responseFactory;

    @Autowired
    private BaseService                       baseService;

    @Autowired
    private StorageService                    storageService;

    @Autowired
    private StorageExecutionControllerFactory factory;

    @PostMapping
    public @ResponseBody ResponseEntity<?> create( @RequestBody ObjectNode json )
    {
        StorageExecutionAPICreateDto createDto = factory.createCreateDTO( json );

        StorageExecution domain = storageService.createExecution( createDto.toCreateDto( baseService ) );
        StorageExecutionResponseDTO reponseDto = factory.createResponseDTO( domain );
        return ResponseEntity.status( HttpStatus.CREATED ).body( responseFactory.createResponse( reponseDto ) );
    }

    // TODO: addResponse
    @PostMapping( value = "/updateById/{id}/addPosition" )
    public @ResponseBody ResponseEntity<?> addPosition( @PathVariable long id, @RequestBody ObjectNode json )
    {
        StorageExecution storageExecution = findById( id );
        checkCreator( storageExecution );

        StorageExecutionAPIAddPositionDto addPositionDto = factory.createAddPositionDTO( json, storageExecution.getId() );

        storageService.addPosition( addPositionDto.toCreateDto( baseService ) );

//        StorageExecutionResponseDTO reponseDto = factory.createResponseDTO( domain );
        return ResponseEntity.status( HttpStatus.CREATED ).build();
    }

    private StorageExecution findById( long id )
    {
        if ( id <= 0 ) {
            throw APIBadRequestException.create( "id must be greater than 1" );
        }
        StorageExecution domain = baseService.getMananger().find( StorageExecution.class, id );
        if ( domain == null ) {
            throw APIEntityNotFoundException.create( StorageExecution.class, "id", id );
        }
        return domain;
    }

    private void checkCreator( StorageExecution domain )
    {
        long id = domain.getCreatedBy().getId();
        Person caller = baseService.getCaller();
        if ( id != caller.getId() ) {
            throw APIMissingAuthorizationException.create( "Storage execution [" + domain.getId() + "] was not created by caller [" + caller.getId() + "]!" );
        }
    }

}