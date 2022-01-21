package com.thumann.server.web.controller.employee;

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
import com.thumann.server.service.helper.UserThreadHelper;
import com.thumann.server.service.tenant.TenantService;
import com.thumann.server.service.user.EmployeeService;
import com.thumann.server.web.exception.APIMissingAuthorizationException;
import com.thumann.server.web.exception.APINumberConflictException;
import com.thumann.server.web.helper.search.ApiSearchHelper;
import com.thumann.server.web.response.ReponseFactory;

@RestController
@RequestMapping( value = "/employee", consumes = "application/json", produces = "application/json" )
public class EmployeeController
{
    @Autowired
    private ReponseFactory            responseFactory;

    @Autowired
    private EmployeeService           employeeService;

    @Autowired
    private TenantService             tenantService;

    @Autowired
    private BaseService               baseService;

    @Autowired
    private EmployeeControllerFactory factory;

    @PostMapping
    public @ResponseBody ResponseEntity<?> createEmployee( @RequestBody ObjectNode json )
    {
        checkPrivilege();

        EmployeeCreateDTO createDto = factory.createCreateDTO( json, tenantService );
        Employee existingEmployee = employeeService.getByUsername( createDto.getUserName() );
        if ( existingEmployee != null ) {
            throw APINumberConflictException.create( Employee.class, "username", createDto.getUserName() );
        }

        Employee employee = employeeService.createEmployee( createDto );
        EmployeeResponseDTO reponseDto = factory.createResponseDTO( employee.getId() );
        return ResponseEntity.status( HttpStatus.CREATED ).body( responseFactory.createResponse( reponseDto ) );
    }

    @PostMapping( value = "/search" )
    public @ResponseBody ResponseEntity<?> search( @RequestBody ObjectNode json )
    {
        checkPrivilege();

        EmployeeSearchParams searchParams = new EmployeeSearchParams( factory );
        searchParams.initParams( json );

        ApiSearchHelper searchHelper = new ApiSearchHelper( baseService );
        JsonNode result = searchHelper.executeSearch( searchParams );

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

//  @GetMapping( value = "/getByUsername/{username}" )
//  public @ResponseBody ResponseEntity<?> getByUsername( @PathVariable String username )
//  {
//      if ( StringUtil.isEmpty( username ) ) {
//          throw APIBadRequestException.create( "username must be given" );
//      }
//      Employee employee = employeeService.getByUsername( username );
//      if ( employee == null ) {
//          throw APIEntityNotFoundException.create( Employee.class, "username", username );
//      }
//      EmployeeResponseDTO reponseDto = factory.createResponseDTO( employee );
//      return ResponseEntity.status( HttpStatus.OK ).body( responseFactory.createResponse( reponseDto ) );
//  }

//
//    @GetMapping( value = "/getByUsername/{username}" )
//    public @ResponseBody ResponseEntity<?> getByUsername( @PathVariable String username )
//    {
//        if ( StringUtil.isEmpty( username ) ) {
//            throw APIBadRequestException.create( "username must be given" );
//        }
//        Employee employee = employeeService.getByUsername( username );
//        if ( employee == null ) {
//            throw APIEntityNotFoundException.create( Employee.class, "username", username );
//        }
//        EmployeeResponseDTO reponseDto = factory.createResponseDTO( employee );
//        return ResponseEntity.status( HttpStatus.OK ).body( responseFactory.createResponse( reponseDto ) );
//    }

    private void checkPrivilege()
    {
        Employee caller = baseService.getById( UserThreadHelper.getUser(), Employee.class, CollectionUtil.setOf( "privilege" ) );
        if ( !caller.getPrivilege().isSystemConfiguration() ) {
            throw APIMissingAuthorizationException.create( "systemConfiguration" );
        }
    }

}