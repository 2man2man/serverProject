package com.thumann.server.helper.collection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.thumann.server.domain.Domain;

public class CollectionUtil
{
    public static Set<String> setOf( String... strings )
    {
        Set<String> result = new HashSet<String>();
        for ( String string : strings ) {
            result.add( string );
        }
        return result;
    }

    public static <T> List<T> safeSubList( List<T> list, int fromIndex, int toIndex )
    {
        int size = list.size();
        if ( fromIndex >= size || toIndex <= 0 || fromIndex >= toIndex ) {
            return new ArrayList<T>();
        }

        fromIndex = Math.max( 0, fromIndex );
        toIndex = Math.min( size, toIndex );

        return list.subList( fromIndex, toIndex );
    }

    public static List<Long> getIdsAsList( Iterable<? extends Domain> domains )
    {
        List<Long> result = new ArrayList<Long>();
        for ( Iterator<? extends Domain> iterator = domains.iterator(); iterator.hasNext(); ) {
            Domain obj = iterator.next();
            result.add( obj.getId() );
        }
        return result;
    }

    public static Set<Long> getIdsAsSet( Iterable<? extends Domain> domains )
    {
        Set<Long> result = new HashSet<>();
        for ( Iterator<? extends Domain> iterator = domains.iterator(); iterator.hasNext(); ) {
            Domain obj = iterator.next();
            result.add( obj.getId() );
        }
        return result;
    }

}
