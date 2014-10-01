package com.daren.core.web.component;

import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * Created by sunlf on 14-3-23.
 */
public class InitActivator implements BundleActivator {

    private static BundleContext context;
    private static Logger logger = Logger.getLogger(InitActivator.class);
    private ServiceReference reference;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        logger.info("Start-up Urgent :: Core - Web Component!!");
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        logger.info("Stop Urgent :: Core - Web Component!!");
    }
}
