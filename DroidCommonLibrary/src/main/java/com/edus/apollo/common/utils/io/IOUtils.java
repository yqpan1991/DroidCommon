package com.edus.apollo.common.utils.io;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by panyongqiang on 2016/6/28.
 */
public class IOUtils {
    public static void closeSilently(Closeable closeable){
        if(closeable != null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
