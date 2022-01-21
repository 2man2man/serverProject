package com.thumann.server.helper.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.thumann.server.helper.date.DateUtil;
import com.thumann.server.helper.string.StringUtil;

public class JsonUtil
{
    public static ObjectNode putStringArray( ObjectNode node, String key, List<String> strings )
    {
        ArrayNode array = node.putArray( key );
        for ( String string : strings ) {
            array.add( string );
        }
        return node;
    }

    public static ObjectNode putArray( ObjectNode node, String key, List<JsonNode> jsons )
    {
        ArrayNode array = node.putArray( key );
        for ( JsonNode json : jsons ) {
            array.add( json );
        }
        return node;
    }

    public static String getString( ObjectNode node, String property )
    {
        if ( node == null ) {
            return null;
        }
        else if ( StringUtil.isEmpty( property ) ) {
            return null;
        }
        else if ( !node.hasNonNull( property ) ) {
            return null;
        }
        JsonNode jsonNode = node.get( property );
        if ( !( jsonNode instanceof TextNode ) ) {
            return null;
        }
        TextNode textNode = (TextNode) jsonNode;
        return textNode.asText();
    }

    public static Boolean getBoolean( ObjectNode node, String property, Boolean defaultValue )
    {
        if ( node == null ) {
            return defaultValue;
        }
        else if ( StringUtil.isEmpty( property ) ) {
            return defaultValue;
        }
        else if ( !node.hasNonNull( property ) ) {
            return defaultValue;
        }
        JsonNode jsonNode = node.get( property );
        if ( !( jsonNode instanceof BooleanNode ) ) {
            return null;
        }
        BooleanNode textNode = (BooleanNode) jsonNode;
        return textNode.asBoolean();
    }

    public static Integer getInteger( ObjectNode node, String property, Integer defaultValue )
    {
        if ( node == null ) {
            return defaultValue;
        }
        else if ( StringUtil.isEmpty( property ) ) {
            return defaultValue;
        }
        else if ( !node.hasNonNull( property ) ) {
            return defaultValue;
        }
        JsonNode jsonNode = node.get( property );
        if ( !( jsonNode instanceof NumericNode ) ) {
            return null;
        }
        NumericNode textNode = (NumericNode) jsonNode;
        return textNode.asInt();
    }

    public static List<String> getStringArray( ObjectNode node, String property )
    {
        List<String> result = new ArrayList<String>();
        if ( node == null ) {
            return result;
        }
        else if ( StringUtil.isEmpty( property ) ) {
            return result;
        }
        else if ( !node.hasNonNull( property ) ) {
            return result;
        }

        JsonNode jsonNode = node.get( property );
        if ( !jsonNode.isArray() ) {
            return result;
        }
        for ( JsonNode innerNode : jsonNode ) {
            if ( !( innerNode instanceof TextNode ) ) {
                continue;
            }
            result.add( innerNode.asText() );
        }
        return result;
    }

    public static Date getDate( ObjectNode node, String property )
    {
        if ( node == null ) {
            return null;
        }
        else if ( StringUtil.isEmpty( property ) ) {
            return null;
        }
        else if ( !node.hasNonNull( property ) ) {
            return null;
        }
        JsonNode jsonNode = node.get( property );
        if ( !( jsonNode instanceof TextNode ) ) {
            return null;
        }
        TextNode textNode = (TextNode) jsonNode;
        String dateString = textNode.asText();
        if ( StringUtil.isEmpty( dateString ) ) {
            return null;
        }
        return DateUtil.getDate( dateString );
    }

}
