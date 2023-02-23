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

    public static boolean isLong( String string )
    {
        try {
            Long.valueOf( string );
            return true;
        }
        catch ( Exception e ) {
            return false;
        }
    }

    public static boolean isDifferent( String str1, String str2 )
    {
        if ( str1 == null && str2 == null ) {
            return false;
        }
        else if ( str1 == null || str2 == null ) {
            return true;
        }
        else {
            return !str1.equals( str2 );
        }
    }
}