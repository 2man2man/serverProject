package com.thumann.server.web.controller.employee;

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
import com.thumann.server.helper.collection.CollectionUtil;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.service.base.BaseService;
import com.thumann.server.service.helper.UserThreadHelper;
import com.thumann.server.service.tenant.TenantService;
import com.thumann.server.service.user.EmployeeService;
import com.thumann.server.web.exception.APIBadRequestException;
import com.thumann.server.web.exception.APIEntityNotFoundException;
import com.thumann.server.web.exception.APIMissingAuthorizationException;
import com.thumann.server.web.exception.APIMissingFieldException;
import com.thumann.server.web.exception.APINumberConflictException;
import com.thumann.server.web.helper.search.ApiSearchHelper;
import com.thumann.server.web.response.ReponseFactory;

//TODO: check adding of tenants -> Only tenants can be added which the caller itself has
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
        checkTenants( createDto );

        Employee employee = employeeService.createEmployee( createDto );
        EmployeeResponseDTO reponseDto = factory.createResponseDTO( employee.getId() );
        return ResponseEntity.status( HttpStatus.CREATED ).body( responseFactory.createResponse( reponseDto ) );
    }

    @PutMapping( value = "/updateById/{id}" )
    public @ResponseBody ResponseEntity<?> updateById( @PathVariable long id, @RequestBody ObjectNode json )
    {
        checkPrivilege();

        Employee existingEmployee = baseService.getById( id, Employee.class );
        if ( existingEmployee == null ) {
            throw APIEntityNotFoundException.create( Employee.class, "id", id );
        }
        else if ( employeeService.isAdmin( existingEmployee.getId() ) && existingEmployee.getId() != UserThreadHelper.getUser() ) {
            throw APIMissingAuthorizationException.create( "Admin can't be updated" );
        }

        EmployeeUpdateDTO updateDto = factory.createUpdateDTO( json, tenantService, existingEmployee );
        if ( !StringUtil.isEmpty( updateDto.getUserName() ) ) {
            Employee existingEmployeeByUsername = employeeService.getByUsername( updateDto.getUserName() );
            if ( existingEmployeeByUsername != null && existingEmployeeByUsername.getId() != existingEmployee.getId() ) {
                throw APINumberConflictException.create( Employee.class, "username", updateDto.getUserName() );
            }
        }
        checkTenants( updateDto );

        Employee employee = employeeService.updateEmployee( updateDto );
        EmployeeResponseDTO reponseDto = factory.createResponseDTO( employee.getId() );
        return ResponseEntity.status( HttpStatus.OK ).body( responseFactory.createResponse( reponseDto ) );
    }

    private void checkTenants( EmployeeCreateUpdateDTO dto )
    {
        String error = employeeService.checkTenants( dto );
        if ( StringUtil.isEmpty( error ) ) {
            return;
        }
        throw APIBadRequestException.create( error );
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

    @GetMapping( value = "/getMyself" )
    public @ResponseBody ResponseEntity<?> getMyself()
    {
        Employee myself = baseService.getById( UserThreadHelper.getUser(), Employee.class );
        EmployeeResponseDTO reponseDto = factory.createResponseDTO( myself.getId() );
        return ResponseEntity.status( HttpStatus.OK ).body( responseFactory.createResponse( reponseDto ) );
    }

    @GetMapping( value = "/getById//{id}" )
    public @ResponseBody ResponseEntity<?> getById( @PathVariable long id )
    {
        Employee employee = baseService.getById( id, Employee.class );

        if ( employee == null ) {
            throw APIEntityNotFoundException.create( Employee.class, "id", id );
        }

        EmployeeResponseDTO reponseDto = factory.createResponseDTO( employee.getId() );
        return ResponseEntity.status( HttpStatus.OK ).body( responseFactory.createResponse( reponseDto ) );
    }

    @GetMapping( value = "/getByUserName//{username}" )
    public @ResponseBody ResponseEntity<?> getByUserName( @PathVariable String username )
    {
        if ( StringUtil.isEmpty( username ) ) {
            throw APIMissingFieldException.create( "username" );
        }
        Employee employee = employeeService.getByUsername( username );
        if ( employee == null ) {
            throw APIEntityNotFoundException.create( Employee.class, "username", username );
        }

        EmployeeResponseDTO reponseDto = factory.createResponseDTO( employee.getId() );
        return ResponseEntity.status( HttpStatus.OK ).body( responseFactory.createResponse( reponseDto ) );
    }

    private void checkPrivilege()
    {
        Employee caller = baseService.getById( UserThreadHelper.getUser(), Employee.class, CollectionUtil.setOf( "privilege" ) );
        if ( !caller.getPrivilege().isSystemConfiguration() ) {
            throw APIMissingAuthorizationException.create( "systemConfiguration" );
        }
    }

}