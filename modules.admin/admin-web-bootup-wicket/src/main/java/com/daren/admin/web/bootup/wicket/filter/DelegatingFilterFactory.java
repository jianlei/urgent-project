package com.daren.admin.web.bootup.wicket.filter;

import org.ops4j.pax.wicket.api.ConfigurableFilterConfig;
import org.ops4j.pax.wicket.api.support.AbstractFilterFactory;
import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationException;

import javax.servlet.Filter;
import java.util.Dictionary;

public class DelegatingFilterFactory extends AbstractFilterFactory {

    private Filter filter;

    public DelegatingFilterFactory(BundleContext bundleContext, String applicationName, Integer priority) {
        super(bundleContext, applicationName, priority);
    }

    public Filter createFilter(ConfigurableFilterConfig filterConfig) {
        ClassLoader oldClassloader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(TestFilter.class.getClassLoader());
            TestFilter delegatingFilter = TestFilter.class.newInstance();
            delegatingFilter.setFilter(filter);
            delegatingFilter.init(filterConfig);
            return delegatingFilter;
        } catch (Exception e) {
            throw new IllegalStateException(String.format("Filter %s could not be created for application {}",
                    TestFilter.class.getName(), getApplicationName()), e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassloader);
        }
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    @Override
    public void updated(Dictionary properties) throws ConfigurationException {

    }
}
