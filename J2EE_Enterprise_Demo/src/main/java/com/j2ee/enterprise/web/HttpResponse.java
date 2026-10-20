package com.j2ee.enterprise.web;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles HTTP response status codes, headers, and body transmission over TCP.
 */
public class HttpResponse {
    private final PrintWriter writer;
    private int statusCode = 200;
    private String statusMessage = "OK";
    private final Map<String, String> headers = new HashMap<>();

    public HttpResponse(OutputStream outputStream) {
        this.writer = new PrintWriter(outputStream, true, StandardCharsets.UTF_8);
        this.headers.put("Content-Type", "text/html; charset=UTF-8");
        this.headers.put("Server", "Simulated-J2EE-WebContainer/1.0");
    }

    public void setStatus(int code, String message) {
        this.statusCode = code;
        this.statusMessage = message;
    }

    public void setHeader(String name, String value) {
        headers.put(name, value);
    }

    public void sendHtml(String htmlBody) {
        byte[] bodyBytes = htmlBody.getBytes(StandardCharsets.UTF_8);
        headers.put("Content-Length", String.valueOf(bodyBytes.length));

        // 1. Status Line (HTTP/1.1 200 OK)
        writer.print("HTTP/1.1 " + statusCode + " " + statusMessage + "\r\n");

        // 2. Response Headers
        for (Map.Entry<String, String> header : headers.entrySet()) {
            writer.print(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        writer.print("\r\n"); // Blank line marking end of headers

        // 3. Response Body
        writer.print(htmlBody);
        writer.flush();
    }
}
