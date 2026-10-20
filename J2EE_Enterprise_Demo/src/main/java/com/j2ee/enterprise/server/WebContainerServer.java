package com.j2ee.enterprise.server;

import com.j2ee.enterprise.web.BankingServlet;
import com.j2ee.enterprise.web.HttpRequest;
import com.j2ee.enterprise.web.HttpResponse;
import com.j2ee.enterprise.web.MiniServlet;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Web Container / Application Server:
 * 1. Manages network listening over HTTP (Port 8080).
 * 2. Parses raw HTTP protocol request streams.
 * 3. Manages Servlet Lifecycle (init, service, destroy).
 * 4. Dispatches requests through thread pools.
 */
public class WebContainerServer {
    private static final int PORT = 8080;
    private final Map<String, MiniServlet> servletMapping = new HashMap<>();

    public WebContainerServer() {
        // Register Web Application Servlet Mapping (web.xml / @WebServlet equivalent)
        BankingServlet bankingServlet = new BankingServlet();
        bankingServlet.init();
        servletMapping.put("/app", bankingServlet);
        servletMapping.put("/", bankingServlet);
    }

    public void start() {
        ExecutorService workerPool = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("=================================================================");
            System.out.println(" J2EE Application Server & Web Container Started on Port: " + PORT);
            System.out.println(" Open your browser and navigate to: http://localhost:8080/app");
            System.out.println("=================================================================");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                workerPool.execute(() -> handleConnection(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleConnection(Socket socket) {
        try (InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String requestLine = reader.readLine();

            if (requestLine == null || requestLine.isEmpty()) return;

            // Parse HTTP Request Line (e.g., "GET /app?action=list HTTP/1.1")
            String[] tokens = requestLine.split(" ");
            if (tokens.length < 3) return;

            String method = tokens[0];
            String fullUri = tokens[1];

            // Parse URI and Query Params
            String path = fullUri;
            Map<String, String> queryParams = new HashMap<>();

            if (fullUri.contains("?")) {
                String[] uriParts = fullUri.split("\\?", 2);
                path = uriParts[0];
                String queryString = uriParts[1];
                for (String param : queryString.split("&")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        queryParams.put(URLDecoder.decode(pair[0], "UTF-8"), URLDecoder.decode(pair[1], "UTF-8"));
                    }
                }
            }

            // Read headers
            Map<String, String> headers = new HashMap<>();
            String headerLine;
            while ((headerLine = reader.readLine()) != null && !headerLine.isEmpty()) {
                int colon = headerLine.indexOf(":");
                if (colon != -1) {
                    headers.put(headerLine.substring(0, colon).trim(), headerLine.substring(colon + 1).trim());
                }
            }

            HttpRequest request = new HttpRequest(method, path, headers, queryParams);
            HttpResponse response = new HttpResponse(out);

            // Container route dispatch
            MiniServlet servlet = servletMapping.get(path);
            if (servlet == null) {
                servlet = servletMapping.get("/");
            }

            if (servlet != null) {
                servlet.service(request, response);
            } else {
                response.setStatus(404, "Not Found");
                response.sendHtml("<h1>404 Not Found</h1><p>No mapped servlet found for " + path + "</p>");
            }

        } catch (IOException e) {
            System.err.println("[Web Container] Error handling request: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }

    public static void main(String[] args) {
        new WebContainerServer().start();
    }
}
