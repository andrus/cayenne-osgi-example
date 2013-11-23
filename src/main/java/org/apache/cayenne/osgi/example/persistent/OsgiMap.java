package org.apache.cayenne.osgi.example.persistent;

import org.apache.cayenne.osgi.example.persistent.auto._OsgiMap;

public class OsgiMap extends _OsgiMap {

    private static OsgiMap instance;

    private OsgiMap() {}

    public static OsgiMap getInstance() {
        if(instance == null) {
            instance = new OsgiMap();
        }

        return instance;
    }
}
