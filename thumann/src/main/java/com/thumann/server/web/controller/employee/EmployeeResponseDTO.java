package com.thumann.server.web.controller.employee;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.domain.user.Employee;
import com.thumann.server.helper.date.DateUtil;
import com.thumann.server.web.response.CreateJsonInterface;

public class EmployeeResponseDTO implements CreateJsonInterface
{

    private long   id;

    private String firstName;

    private String lastName;

    private String userName;

    private String dateOfBirth;

    public void initValues( Employee employee )
    {
        setId( employee.getId() );
        setFirstName( employee.getFirstName() );
        setLastName( employee.getLastName() );
        setDateOfBirth( DateUtil.getDateString( employee.getDateOfBirth() ) );
        setUserName( employee.getCredentials().getUsername() );
    }

    @Override
    public JsonNode createJson()
    {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put( "id", getId() );
        objectNode.put( "firstName", getFirstName() );
        objectNode.put( "lastName", getLastName() );
        objectNode.put( "dateOfBirth", getDateOfBirth() );
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

}
