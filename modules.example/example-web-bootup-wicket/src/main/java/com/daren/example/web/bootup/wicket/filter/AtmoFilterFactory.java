package com.daren.example.web.bootup.wicket.filter;

import org.atmosphere.cpr.AtmosphereFilter;
import org.ops4j.pax.wicket.api.ConfigurableFilterConfig;
import org.ops4j.pax.wicket.api.FilterFactory;

import javax.servlet.Filter;
import javax.servlet.ServletException;

public class AtmoFilterFactory implements FilterFactory {



    // --------------------------------------------------------------------------
    // Constructors
    // --------------------------------------------------------------------------

    public AtmoFilterFactory() {
    }

    // --------------------------------------------------------------------------
    // Implementing FilterFactory
    // --------------------------------------------------------------------------

    public Filter createFilter(ConfigurableFilterConfig filterConfig) {
        // Ensure shiro environment is part of the servlet context.
       //ensureShiroEnvironmentInServletContext(filterConfig);
        filterConfig.setFilterName("Atmospherefilter");
        filterConfig.putInitParameter("org.atmosphere.useWebSocket","true");
        filterConfig.putInitParameter("org.atmosphere.useNative","true");
        filterConfig.putInitParameter("urlPatterns","/*");
        filterConfig.putInitParameter("applicationClassName","com.daren.example.web.bootup.wicket.IrisShiroApplication");
        filterConfig.putInitParameter("org.atmosphere.cpr.broadcasterClass","org.atmosphere.cpr.DefaultBroadcaster");

        AtmosphereFilter atmosphereFilter = new AtmosphereFilter();
        try {
            atmosphereFilter.init(filterConfig);



        } catch (ServletException e) {
            e.printStackTrace();
        }
        return atmosphereFilter;
    }

    // --------------------------------------------------------------------------
    // Getters / Setters
    // --------------------------------------------------------------------------




}
