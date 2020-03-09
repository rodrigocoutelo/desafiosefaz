package me.coutelo.desafio.filter;

import java.io.*;

import javax.faces.application.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet Filter implementation class NoCacheFIlter
 */

public class NoCacheFIlter implements Filter {

	/**
	 * Default constructor.
	 */
	public NoCacheFIlter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;

		if (!request.getRequestURI().startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) { // Skip
																													// JSF
																													// resources
																													// (CSS/JS/Images/etc)
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			response.setDateHeader("Expires", 0); // Proxies.
		}

		chain.doFilter(req, res);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(final FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
