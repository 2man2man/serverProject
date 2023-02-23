package com.thumann.server.web.response.pdf;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thumann.server.web.response.APIResponseBuilderHelper;
import com.thumann.server.web.response.CreateJsonInterface;

public class PdfResponseSimple extends APIResponseBuilderHelper implements CreateJsonInterface
{
    private String base64String;

    @Override
    public JsonNode createJson()
    {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        addValue( objectNode, "base64String", base64String );
        return objectNode;
    }

    public void setBase64String( String base64String )
    {
        this.base64String = base64String;
    }

}
