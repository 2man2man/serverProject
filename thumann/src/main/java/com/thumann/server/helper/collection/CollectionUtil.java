package com.thumann.server.helper.collection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
}
