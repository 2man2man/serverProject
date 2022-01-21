package com.thumann.server.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class APIMissingAuthorizationException extends ResponseStatusException
{
    private static final long serialVersionUID = 7744327270117631068L;

    private String            missingAuthorization;

    public static APIMissingAuthorizationException create( String missingAuthorization )
    {
        StringBuilder sb = new StringBuilder();
        sb.append( "The required authorization '" ).append( missingAuthorization ).append( "' is missing." );
        return new APIMissingAuthorizationException( sb.toString() );
    }

    private APIMissingAuthorizationException( String missingField )
    {
        super( HttpStatus.FORBIDDEN, missingField );
    }

    public String getMissingAuthorization()
    {
        return missingAuthorization;
    }

    public void setMissingAuthorization( String missingAuthorization )
    {
        this.missingAuthorization = missingAuthorization;
    }

}
