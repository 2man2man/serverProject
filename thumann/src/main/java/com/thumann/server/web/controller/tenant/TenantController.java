package com.thumann.server.web.controller.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.service.tenant.TenantService;
import com.thumann.server.web.response.ReponseFactory;

@RestController
@RequestMapping( value = "/tenant", consumes = "application/json", produces = "application/json" )
public class TenantController
{
    @Autowired
    private ReponseFactory          responseFactory;

    @Autowired
    private TenantService           tenantService;

    @Autowired
    private TenantControllerFactory factory;

    @PostMapping
    public @ResponseBody ResponseEntity<?> createArticle( @RequestBody ObjectNode json )
    {
        TenantCreateDTO createDto = factory.createCreateDTO( json, tenantService );

        Tenant article = tenantService.createTenant( createDto );
        TenantResponseDTO reponseDto = factory.createResponseDTO( article );
        return ResponseEntity.status( HttpStatus.CREATED ).body( responseFactory.createResponse( reponseDto ) );
    }

}