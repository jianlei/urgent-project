package com.daren.enterprise.webapp.gis.internal;

import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;

/**
 * Created by sunlf on 14-3-23.
 */
public class InitActivator implements BundleActivator {

    private static BundleContext context;
    private static Logger logger = Logger.getLogger(InitActivator.class);
    private ServiceReference reference;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        logger.info("Start-up core web resources bundle!!");
        context = bundleContext;
        reference = bundleContext.getServiceReference(HttpService.class.getName());
        HttpService httpService = (HttpService) bundleContext.getService(reference);
//        httpService.registerResources("/custome/gis", "/custome/gis", null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        logger.info("Stop core web resources bundle!!");
        if (reference != null)
            bundleContext.ungetService(reference);
        context = null;

    }
}
