package com.j2ee.enterprise.web;

import java.util.Collections;
import java.util.Map;

/**
 * Encapsulates the HTTP Protocol components:
 * Method (GET/POST), URI, Version, Headers, and Parameters.
 */
public class HttpRequest {
    private final String method;
    private final String path;
    private final Map<String, String> headers;
    private final Map<String, String> queryParams;

    public HttpRequest(String method, String path, Map<String, String> headers, Map<String, String> queryParams) {
        this.method = method;
        this.path = path;
        this.headers = headers;
        this.queryParams = queryParams;
    }

    public String getMethod() { return method; }
    public String getPath() { return path; }
    public Map<String, String> getHeaders() { return Collections.unmodifiableMap(headers); }
    public String getParameter(String key) { return queryParams.get(key); }
}
