package com.thumann.server.service.helper;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.thumann.server.helper.string.StringUtil;

public class InclExclFieldsHelper
{
    private static ConcurrentMap<Long, Set<String>> inclduedFieldsMap = new ConcurrentHashMap<Long, Set<String>>();

    private static ConcurrentMap<Long, Set<String>> exclduedFieldsMap = new ConcurrentHashMap<Long, Set<String>>();

    public static void addFields( Enumeration<String> fields )
    {
        Set<String> included = new HashSet<String>();
        Set<String> excluded = new HashSet<String>();

        while ( fields.hasMoreElements() ) {
            String field = fields.nextElement();
            if ( StringUtil.isEmpty( field ) ) {
                continue;
            }
            else if ( field.startsWith( "_" ) ) {
                excluded.add( field.substring( 1 ) );
            }
            else {
                included.add( field );
            }
            final long threadId = Thread.currentThread().getId();

            inclduedFieldsMap.put( threadId, included );
            exclduedFieldsMap.put( threadId, excluded );
        }
    }

    public static Set<String> getIncludedFields()
    {
        Set<String> fields = inclduedFieldsMap.get( Thread.currentThread().getId() );
        if ( fields == null ) {
            return new HashSet<String>();
        }
        return new HashSet<String>( fields );
    }

    public static Set<String> getExcludedFields()
    {
        Set<String> fields = exclduedFieldsMap.get( Thread.currentThread().getId() );
        if ( fields == null ) {
            return new HashSet<String>();
        }
        return new HashSet<String>( fields );
    }

    public static void remove()
    {
        final long threadId = Thread.currentThread().getId();
        inclduedFieldsMap.remove( threadId );
        exclduedFieldsMap.remove( threadId );
    }

}
