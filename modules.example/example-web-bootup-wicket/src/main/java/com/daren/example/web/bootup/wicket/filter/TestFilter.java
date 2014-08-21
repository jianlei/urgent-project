package com.daren.example.web.bootup.wicket.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: sunlf
 * Date: 14-2-20
 * Time: 下午6:33
 * To change this template use File | Settings | File Templates.
 */
public class TestFilter implements Filter {
    private static Logger logger = Logger.getLogger(TestFilter.class);
    private Filter filter;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.error("doFilter start---> Address is " + request.getRemoteAddr());
        chain.doFilter(request, response);
    }

    private Filter getFilter() {
        if (filter == null) {
            throw new IllegalStateException("Filter property is required");
        }
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
