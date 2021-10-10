package com.thumann.server.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class APINumberConflictException extends ResponseStatusException
{
    private static final long serialVersionUID = 7744327270117631068L;

    private String            conflictField;

    private String            conflictValue;

    private String            conflictClass;

    public static APINumberConflictException create( Class<?> clazz, String conflictField, String conflictValue )
    {
        String conflictClass = clazz.getSimpleName();
        StringBuilder sb = new StringBuilder();
        sb.append( "There already is an instance of " ).append( conflictClass );
        sb.append( " with value " ).append( conflictValue );
        sb.append( " for identifier value " ).append( conflictField );

        return new APINumberConflictException( conflictClass, conflictField, conflictValue, sb.toString() );
    }

    private APINumberConflictException( String conflictClass, String conflictField, String conflictValue, String message )
    {
        super( HttpStatus.CONFLICT, message );
        this.conflictField = conflictField;
        this.conflictValue = conflictValue;
        this.conflictClass = conflictClass;
    }

    public String getConflictField()
    {
        return conflictField;
    }

    public void setConflictField( String conflictField )
    {
        this.conflictField = conflictField;
    }

    public String getConflictValue()
    {
        return conflictValue;
    }

    public void setConflictValue( String conflictValue )
    {
        this.conflictValue = conflictValue;
    }

    public String getConflictClass()
    {
        return conflictClass;
    }

    public void setConflictClass( String conflictClass )
    {
        this.conflictClass = conflictClass;
    }

}
