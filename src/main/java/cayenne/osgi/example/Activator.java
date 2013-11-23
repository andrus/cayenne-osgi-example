/*****************************************************************
 *   Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 ****************************************************************/
package cayenne.osgi.example;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.osgi.OsgiModule;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.di.Module;
import org.apache.cayenne.map.EntityResolver;
import org.apache.cayenne.map.ObjEntity;
import org.apache.cayenne.query.SelectQuery;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import cayenne.osgi.example.persistent.Entity1;

public class Activator implements BundleActivator {

    private ServerRuntime cayenneRuntime;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Starting Cayenne OSGi example");

        this.cayenneRuntime = loadCayenneRuntime("cayenne-osgi-example.xml");

        // Cayenne is up by this point, we can run some operations and check
        // that they do not fail
        testStartup();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Stopping Cayenne OSGi example");
    }

    private ServerRuntime loadCayenneRuntime(String name) {
        ClassLoader oldCL = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(Activator.class.getClassLoader());
            Module osgiExtentionsModule = new OsgiModule();
            ServerRuntime cayenneRuntime = new ServerRuntime("cayenne-osgi-example.xml", osgiExtentionsModule);

            // warm up ServerRuntime for class loading to occur under the
            // current thread context
            EntityResolver entityResolver = cayenneRuntime.getChannel().getEntityResolver();
            for (ObjEntity e : entityResolver.getObjEntities()) {

                // it is not enough to just call 'getObjectClass()' on
                // ClassDescriptor - there's an optimization that prevents full
                // descriptor resolving... so calling some other method...
                entityResolver.getClassDescriptor(e.getName()).getProperty("__dummy__");
                entityResolver.getCallbackRegistry();
            }

            return cayenneRuntime;
        } finally {
            Thread.currentThread().setContextClassLoader(oldCL);
        }
    }

    private void testStartup() {

        ObjectContext context = cayenneRuntime.newContext();
        Entity1 e1 = context.newObject(Entity1.class);
        e1.setName("E1_" + System.currentTimeMillis());
        context.commitChanges();

        SelectQuery<Entity1> query = SelectQuery.query(Entity1.class);
        context.select(query);
    }
}