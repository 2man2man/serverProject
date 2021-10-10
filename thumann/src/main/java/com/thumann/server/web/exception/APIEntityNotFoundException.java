package com.thumann.server.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class APIEntityNotFoundException extends ResponseStatusException
{
    private static final long serialVersionUID = 7744327270117631068L;

    private String            fieldIdentifier;

    private String            fieldValue;

    private String            entityClass;

    public static APIEntityNotFoundException create( Class<?> clazz, String fieldIdentifier, long fieldValue )
    {
        return create( clazz, fieldIdentifier, String.valueOf( fieldValue ) );
    }

    public static APIEntityNotFoundException create( Class<?> clazz, String fieldIdentifier, String fieldValue )
    {
        String entityClass = clazz.getSimpleName();
        StringBuilder sb = new StringBuilder();
        sb.append( "No instance of '" ).append( entityClass );
        sb.append( "' with value '" ).append( fieldValue );
        sb.append( "' for identifier '" ).append( fieldIdentifier );
        sb.append( "' could be found. " );

        return new APIEntityNotFoundException( entityClass, fieldIdentifier, fieldValue, sb.toString() );
    }

    private APIEntityNotFoundException( String entityClass, String fieldIdentifier, String fieldValue, String message )
    {
        super( HttpStatus.NOT_FOUND, message );
        this.fieldIdentifier = fieldIdentifier;
        this.fieldValue = fieldValue;
        this.entityClass = entityClass;
    }

    public String getFieldIdentifier()
    {
        return fieldIdentifier;
    }

    public void setFieldIdentifier( String fieldIdentifier )
    {
        this.fieldIdentifier = fieldIdentifier;
    }

    public String getFieldValue()
    {
        return fieldValue;
    }

    public void setFieldValue( String fieldValue )
    {
        this.fieldValue = fieldValue;
    }

    public String getEntityClass()
    {
        return entityClass;
    }

    public void setEntityClass( String entityClass )
    {
        this.entityClass = entityClass;
    }

}
