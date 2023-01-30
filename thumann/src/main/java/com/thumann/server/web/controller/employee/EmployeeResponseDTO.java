package com.thumann.server.web.controller.employee;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.tenant.Tenant;
import com.thumann.server.domain.user.Employee;
import com.thumann.server.helper.json.JsonUtil;
import com.thumann.server.web.response.CreateJsonInterface;

public class EmployeeResponseDTO implements CreateJsonInterface
{
    private long         id;

    private String       firstName;

    private String       lastName;

    private String       userName;

    private String       dateOfBirth;

    private boolean      systemConfigurationPrivilege = true;

    private boolean      articleModifyPrivilege       = true;

    private List<String> tenants                      = new ArrayList<>();

    public void initValues( Employee employee )
    {
        setId( employee.getId() );
        setFirstName( employee.getFirstName() );
        setLastName( employee.getLastName() );
        setUserName( employee.getCredentials().getUsername() );
        setSystemConfigurationPrivilege( employee.getPrivilege().isSystemConfiguration() );
        setArticleModifyPrivilege( employee.getPrivilege().isArticleModify() );

        for ( Tenant tenant : employee.getTenants() ) {
            tenants.add( tenant.getNumber() );
        }
    }

    @Override
    public JsonNode createJson()
    {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put( "id", getId() );
        objectNode.put( "firstName", getFirstName() );
        objectNode.put( "lastName", getLastName() );
        objectNode.put( "userName", getUserName() );
        objectNode.put( "dateOfBirth", getDateOfBirth() );
        objectNode.put( "systemConfigurationPrivilege", isSystemConfigurationPrivilege() );
        objectNode.put( "articleModifyPrivilege", isArticleModifyPrivilege() );

        JsonUtil.putStringArray( objectNode, "tenants", tenants );

        return objectNode;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public String getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth( String dateOfBirth )
    {
        this.dateOfBirth = dateOfBirth;
    }

    public long getId()
    {
        return id;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName( String userName )
    {
        this.userName = userName;
    }

    public boolean isSystemConfigurationPrivilege()
    {
        return systemConfigurationPrivilege;
    }

    public void setSystemConfigurationPrivilege( boolean systemConfigurationPrivilege )
    {
        this.systemConfigurationPrivilege = systemConfigurationPrivilege;
    }

    public boolean isArticleModifyPrivilege()
    {
        return articleModifyPrivilege;
    }

    public void setArticleModifyPrivilege( boolean articleModifyPrivilege )
    {
        this.articleModifyPrivilege = articleModifyPrivilege;
    }

}
