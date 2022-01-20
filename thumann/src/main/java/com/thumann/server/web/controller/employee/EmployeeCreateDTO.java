package com.thumann.server.web.controller.employee;

import java.util.Date;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.helper.json.JsonUtil;
import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.web.exception.APIMissingFieldException;

public class EmployeeCreateDTO
{
    private String firstName;

    private String lastName;

    private String userName;

    private String password;

    private Date   dateOfBirth;

    public void initValues( ObjectNode json )
    {
        setFirstName( JsonUtil.getString( json, "firstName" ) );
        setLastName( JsonUtil.getString( json, "lastName" ) );
        setUserName( JsonUtil.getString( json, "userName" ) );
        setPassword( JsonUtil.getString( json, "password" ) );
        setDateOfBirth( JsonUtil.getDate( json, "dateOfBirth" ) );
    }

    public void checkRequiredFields()
    {
        if ( StringUtil.isEmpty( getFirstName() ) ) {
            throw APIMissingFieldException.create( "firstName" );
        }
        else if ( StringUtil.isEmpty( getLastName() ) ) {
            throw APIMissingFieldException.create( "lastName" );
        }
        else if ( StringUtil.isEmpty( getUserName() ) ) {
            throw APIMissingFieldException.create( "userName" );
        }
        else if ( StringUtil.isEmpty( getPassword() ) ) {
            throw APIMissingFieldException.create( "password" );
        }

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

    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth( Date dateOfBirth )
    {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName( String userName )
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

}
