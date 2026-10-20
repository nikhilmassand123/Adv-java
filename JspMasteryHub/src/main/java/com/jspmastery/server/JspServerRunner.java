package com.jspmastery.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import java.io.File;

/**
 * Embedded Web Container launcher configured to parse and execute JSPs.
 */
public class JspServerRunner {
    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        Server server = new Server(PORT);

        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        
        File warDir = new File("src/main/webapp");
        webapp.setResourceBase(warDir.exists() ? "src/main/webapp" : ".");
        webapp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                ".*/[^/]*taglibs.*\\.jar$|.*/jakarta.servlet.jsp.jstl.*\\.jar$");

        server.setHandler(webapp);

        System.out.println("=================================================================");
        System.out.println(" JSP Web Application Container Booting on Port: " + PORT);
        System.out.println(" Open your browser and navigate to: http://localhost:8080/index.jsp");
        System.out.println("=================================================================");

        server.start();
        server.join();
    }
}
