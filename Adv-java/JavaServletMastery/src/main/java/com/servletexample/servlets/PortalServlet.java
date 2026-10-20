package com.servletexample.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Demonstrates:
 * 1. Servlet Lifecycle: init(), service() -> doGet()/doPost(), destroy()
 * 2. ServletConfig & ServletContext parameter retrieval
 * 3. HttpServletRequest and HttpServletResponse manipulation
 * 4. HttpSession handling (attributes, invalidation, timeout)
 */
public class PortalServlet extends HttpServlet {

    private String departmentName;

    // 1. Lifecycle: init() - executed once when the servlet instance is loaded
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // Retrieve ServletConfig parameter (configured in web.xml)
        this.departmentName = config.getInitParameter("departmentName");
        System.out.println("[Servlet Lifecycle] init() executed for PortalServlet. Department: " + departmentName);
    }

    // 2. Lifecycle: doGet() - handles HTTP GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Session Handling
        // true creates a new session if one does not already exist
        HttpSession session = request.getSession(true);

        String action = request.getParameter("action");
        if ("logout".equalsIgnoreCase(action)) {
            session.invalidate(); // Destroys session and triggers HttpSessionListener
            response.sendRedirect(request.getContextPath() + "/portal");
            return;
        }

        // Session Attribute tracking
        Integer visitCount = (Integer) session.getAttribute("visitCount");
        if (visitCount == null) {
            visitCount = 1;
        } else {
            visitCount++;
        }
        session.setAttribute("visitCount", visitCount);

        // Reading ServletContext (Application Scope)
        ServletContext context = getServletContext();
        String appEnv = context.getInitParameter("applicationEnvironment");
        String adminEmail = context.getInitParameter("adminEmail");
        AtomicInteger activeSessions = (AtomicInteger) context.getAttribute("activeSessionsCounter");

        // HttpServletResponse setup
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Java Servlet Architecture &amp; Lifecycle</title>");
        out.println("<style>");
        out.println("body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; margin: 30px; background: #fafafa; }");
        out.println(".card { background: white; border-radius: 8px; padding: 20px; box-shadow: 0 2px 6px rgba(0,0,0,0.1); margin-bottom: 20px; }");
        out.println("h2 { color: #1a365d; border-bottom: 2px solid #e2e8f0; padding-bottom: 8px; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 10px; }");
        out.println("th, td { border: 1px solid #cbd5e1; padding: 10px; text-align: left; }");
        out.println("th { background: #f1f5f9; }");
        out.println(".btn { display: inline-block; padding: 8px 16px; background: #2563eb; color: white; text-decoration: none; border-radius: 4px; }");
        out.println(".btn-danger { background: #dc2626; }");
        out.println("</style></head><body>");

        out.println("<div class='card'>");
        out.println("<h1>Java Servlet Engine &amp; HTTP Runtime</h1>");
        out.println("<p>Demonstrating Lifecycle, Config, Context, Request/Response, Sessions &amp; Filters.</p>");
        out.println("</div>");

        // ServletConfig & Context details
        out.println("<div class='card'>");
        out.println("<h2>1. Configuration &amp; Context Scope</h2>");
        out.println("<table>");
        out.println("<tr><th>Scope</th><th>Key</th><th>Value</th><th>Source</th></tr>");
        out.println("<tr><td>ServletConfig (Local)</td><td>departmentName</td><td>" + departmentName + "</td><td>Servlet init-param (web.xml)</td></tr>");
        out.println("<tr><td>ServletContext (Global)</td><td>applicationEnvironment</td><td>" + appEnv + "</td><td>Context-param</td></tr>");
        out.println("<tr><td>ServletContext (Global)</td><td>adminEmail</td><td>" + adminEmail + "</td><td>Context-param</td></tr>");
        out.println("<tr><td>ServletContext Attribute</td><td>activeSessionsCounter</td><td>" + (activeSessions != null ? activeSessions.get() : "N/A") + "</td><td>AppLifecycleListener</td></tr>");
        out.println("</table>");
        out.println("</div>");

        // Request Object Examination
        out.println("<div class='card'>");
        out.println("<h2>2. HttpServletRequest Object Inspection</h2>");
        out.println("<table>");
        out.println("<tr><th>Attribute</th><th>Value</th></tr>");
        out.println("<tr><td>HTTP Method</td><td>" + request.getMethod() + "</td></tr>");
        out.println("<tr><td>Request Protocol</td><td>" + request.getProtocol() + "</td></tr>");
        out.println("<tr><td>Client IP Address</td><td>" + request.getRemoteAddr() + "</td></tr>");
        out.println("<tr><td>User-Agent</td><td>" + request.getHeader("User-Agent") + "</td></tr>");
        out.println("<tr><td>Query String</td><td>" + request.getQueryString() + "</td></tr>");
        out.println("</table>");
        out.println("</div>");

        // Session Handling
        out.println("<div class='card'>");
        out.println("<h2>3. Session Handling (HttpSession)</h2>");
        out.println("<table>");
        out.println("<tr><th>Property</th><th>Value</th></tr>");
        out.println("<tr><td>Session ID</td><td><code>" + session.getId() + "</code></td></tr>");
        out.println("<tr><td>Creation Time</td><td>" + new Date(session.getCreationTime()) + "</td></tr>");
        out.println("<tr><td>Last Accessed Time</td><td>" + new Date(session.getLastAccessedTime()) + "</td></tr>");
        out.println("<tr><td>Visit Count (Session Scope)</td><td><strong>" + visitCount + "</strong></td></tr>");
        out.println("</table>");
        out.println("<br>");
        out.println("<a href='" + request.getContextPath() + "/portal' class='btn'>Refresh Page</a> ");
        out.println("<a href='" + request.getContextPath() + "/portal?action=logout' class='btn btn-danger'>Invalidate Session</a>");
        out.println("</div>");

        // Form Submission to test POST handling
        out.println("<div class='card'>");
        out.println("<h2>4. Test HttpServletResponse &amp; doPost()</h2>");
        out.println("<form method='POST' action='" + request.getContextPath() + "/portal'>");
        out.println("<label>Enter Data to Post: </label>");
        out.println("<input type='text' name='payload' placeholder='e.g., sample payload' required /> ");
        out.println("<input type='submit' class='btn' value='Submit POST Request' />");
        out.println("</form>");
        out.println("</div>");

        out.println("</body></html>");
    }

    // Lifecycle: doPost() - handles HTTP POST requests
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String payload = request.getParameter("payload");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html><html><body>");
        out.println("<h2>POST Request Received Successfully</h2>");
        out.println("<p>Submitted payload: <strong>" + (payload != null ? payload : "Empty") + "</strong></p>");
        out.println("<a href='" + request.getContextPath() + "/portal'>Return to Portal</a>");
        out.println("</body></html>");
    }

    // 3. Lifecycle: destroy() - executed when unloading the servlet
    @Override
    public void destroy() {
        System.out.println("[Servlet Lifecycle] destroy() called for PortalServlet.");
        super.destroy();
    }
}
