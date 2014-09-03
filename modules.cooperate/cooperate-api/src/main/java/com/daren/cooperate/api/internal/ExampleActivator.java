package com.daren.cooperate.api.internal;

import com.daren.accident.api.ExampleService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.Dictionary;
import java.util.Properties;

/**
 * Extension of the default OSGi bundle activator
 */
public final class ExampleActivator
        implements BundleActivator {
    /**
     * Called whenever the OSGi framework starts our bundle
     */
    public void start(BundleContext bc)
            throws Exception {
        System.out.println("STARTING com.daren.accident");

        Dictionary props = new Properties();
        // add specific service properties here...

        System.out.println("REGISTER com.daren.accident.api.ExampleService");

        // Register our example service implementation in the OSGi service registry
        bc.registerService(ExampleService.class.getName(), new ExampleServiceImpl(), props);
    }

    /**
     * Called whenever the OSGi framework stops our bundle
     */
    public void stop(BundleContext bc)
            throws Exception {
        System.out.println("STOPPING com.daren.accident");

        // no need to unregister our service - the OSGi framework handles it for us
    }
}

