package com.vpa.saas.filter.logger;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.vpa.saas.filter.logger.requestlogger.HttpServletRequestCopier;
import com.vpa.saas.filter.logger.responselogger.HttpServletResponseCopier;

/**
 * @author PD42694
 * VPASaaSLoggingFilter filter is used to log the response if logger level is set as debug.  *
 */
public class VPASaaSLoggingFilter implements Filter {

	private static final Logger logger = Logger.getLogger(VPASaaSLoggingFilter.class);

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		if(!logger.isDebugEnabled()){
			filterChain.doFilter(request, response);
		}else{
			if (response.getCharacterEncoding() == null) {
				response.setCharacterEncoding("UTF-8"); // Or whatever default.
														// UTF-8 is good for
														// World Domination.
			} else {
				HttpServletRequestCopier requestCopier = new HttpServletRequestCopier(
						(HttpServletRequest)request);
				
				HttpServletResponseCopier responseCopier = new HttpServletResponseCopier(
						(HttpServletResponse) response);
				
				try {
					filterChain.doFilter(requestCopier, responseCopier);
					responseCopier.flushBuffer();
					requestCopier.finishReading();
				} finally {
					byte[] copy = requestCopier.getCopy();
					logger.debug("Request JSON:"+ new String(copy, response
							.getCharacterEncoding()));
					
							copy = responseCopier.getCopy();
					logger.debug("Response JSON:"+ new String(copy, response
							.getCharacterEncoding()));
					
					
				}
			}
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}

}
