package com.daren.example.web.bootup.wicket;

import org.apache.wicket.protocol.http.WicketFilter;
import org.atmosphere.config.service.AtmosphereHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map.Entry;

@AtmosphereHandlerService
public class DebugWicketFilter extends WicketFilter {
	private static final Logger log = LoggerFactory
			.getLogger(DebugWicketFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		log.info("URI: " + request.toString());
		StringBuilder sb = new StringBuilder("{");
		boolean first = true;
		for (Object curEntry : request.getParameterMap().entrySet()) {
			if (!first)
				sb.append(", ");
			Entry<?, ?> entry = (Entry<?, ?>) curEntry;
			sb.append(entry.getKey()).append("=")
					.append(Arrays.toString((Object[]) entry.getValue()));
			first = false;
		}
		sb.append("}");
		log.info("PAR: " + sb.toString());
		log.info("QUE: " + ((HttpServletRequest) request).getQueryString());
		super.doFilter(request, response, chain);
	}
}
