package com.servletexample.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter Introduction & Implementation:
 * Intercepts incoming requests before reaching the Servlet, and inspects
 * the response before returning to the client.
 */
@WebFilter(filterName = "AuditLoggingFilter", urlPatterns = {"/*"})
public class AuditLoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("[Filter] AuditLoggingFilter initialized.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        long startTime = System.currentTimeMillis();
        String uri = req.getRequestURI();
        String method = req.getMethod();

        System.out.println("[Filter Pre-processing] Incoming " + method + " request to: " + uri);

        // Custom header injected into the response
        res.setHeader("X-Audit-Filtered-By", "AuditLoggingFilter");

        // Pass control along the filter chain to the servlet or next filter
        chain.doFilter(request, response);

        // Post-processing execution timing
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("[Filter Post-processing] Completed " + uri + " in " + duration + " ms");
    }

    @Override
    public void destroy() {
        System.out.println("[Filter] AuditLoggingFilter destroyed.");
    }
}
