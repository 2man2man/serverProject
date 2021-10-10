package com.thumann.server.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class APIBadRequestException extends ResponseStatusException
{
    private static final long serialVersionUID = 7744327270117631068L;

    public static APIBadRequestException create( String message )
    {
        return new APIBadRequestException( message );
    }

    private APIBadRequestException( String message )
    {
        super( HttpStatus.BAD_REQUEST, message );
    }

}