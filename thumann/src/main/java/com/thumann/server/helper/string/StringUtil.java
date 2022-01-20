package com.thumann.server.helper.string;

import java.util.Iterator;

public class StringUtil
{

    public static boolean isEmpty( String string )
    {
        return string == null || string.trim() == "";
    }

    public static String combineWithSeparator( Iterable<?> objects, String separator )
    {
        String sep = "";
        StringBuilder sb = new StringBuilder();
        for ( Iterator<?> iterator = objects.iterator(); iterator.hasNext(); ) {
            sb.append( sep );
            Object obj = iterator.next();
            sb.append( String.valueOf( obj ) );
            sep = separator;
        }
        return sb.toString();
    }

}
