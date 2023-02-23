package com.thumann.server.web.controller.employee;

import com.thumann.server.helper.string.StringUtil;
import com.thumann.server.web.exception.APIMissingFieldException;

public class EmployeeCreateDTO extends EmployeeCreateUpdateDTO
{
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
        else if ( getTenants().isEmpty() ) {
            throw APIMissingFieldException.create( "tenants" );
        }
        else if ( getWarehouse() == null ) {
            throw APIMissingFieldException.create( "warehouse" );
        }
    }
}
