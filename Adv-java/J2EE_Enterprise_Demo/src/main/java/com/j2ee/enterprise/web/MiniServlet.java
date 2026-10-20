package com.j2ee.enterprise.web;

/**
 * Demonstrates the Web Container Servlet contract and lifecycle:
 * init() -> service() -> destroy()
 */
public interface MiniServlet {
    void init();
    void service(HttpRequest req, HttpResponse res);
    void destroy();
}
