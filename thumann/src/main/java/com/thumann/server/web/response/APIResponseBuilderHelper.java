package com.thumann.server.web.response;

import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.service.helper.InclExclFieldsHelper;

public abstract class APIResponseBuilderHelper
{
    private final Set<String> includedFields;

    private Set<String>       excludedFields;

    public APIResponseBuilderHelper()
    {
        includedFields = InclExclFieldsHelper.getIncludedFields();
        excludedFields = InclExclFieldsHelper.getExcludedFields();
    }

    protected boolean isFieldincluded( String fieldName )
    {
        if ( !includedFields.isEmpty() ) {
            if ( !includedFields.contains( fieldName ) ) {
                return false;
            }
        }
        if ( !excludedFields.isEmpty() ) {
            if ( excludedFields.contains( fieldName ) ) {
                return false;
            }
        }
        return true;
    }

    protected void addValue( ObjectNode node, String key, Object value )
    {
        if ( !isFieldincluded( key ) ) {
            return;
        }

        if ( value instanceof String ) {
            node.put( key, (String) value );
        }
        else if ( value instanceof Boolean ) {
            node.put( key, (Boolean) value );
        }
        else if ( value instanceof Long ) {
            node.put( key, (Long) value );
        }
        else if ( value instanceof Integer ) {
            node.put( key, (Integer) value );
        }
        else if ( value instanceof JsonNode ) {
            node.set( key, (JsonNode) value );
        }
        else if ( value instanceof CreateJsonInterface ) {
            node.set( key, ( (CreateJsonInterface) value ).createJson() );
        }
        else {
            throw new IllegalArgumentException( "Given datatype not supported: " + value.getClass().getSimpleName() );
        }

    }

}
