package com.thumann.server.helper.io;

import java.io.Closeable;

public class IOUtill
{
    public static void safeClose( Closeable closeable )
    {
        if ( closeable == null ) {
            return;
        }
        try {
            closeable.close();
        }
        catch ( Exception e ) {
        }
    }
}
