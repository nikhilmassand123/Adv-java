# Java Servlet Architecture, Lifecycle & Web Concepts

A complete Jakarta/Java EE Servlet application showcasing core server-side concepts:

### Key Components Included:

1. **HTTP and Servlet Model**:
   - `HttpServlet` acting as the bridge between standard HTTP protocols (GET, POST, status codes, headers) and Java application logic.

2. **Servlet API & Lifecycle**:
   - `init(ServletConfig)`: Initializes the servlet instance once upon instantiation.
   - `service(HttpServletRequest, HttpServletResponse)`: Routes requests to `doGet()`, `doPost()`, etc.
   - `destroy()`: Performs resource de-allocation prior to garbage collection.

3. **ServletConfig vs. ServletContext**:
   - `ServletConfig`: Servlet-specific initialization parameters (`init-param` in `web.xml`).
   - `ServletContext`: Application-wide environment attributes (`context-param` and shared attributes across all servlets).

4. **Request & Response Objects**:
   - `HttpServletRequest`: Reading HTTP parameters, headers, client IP, method, and session binding.
   - `HttpServletResponse`: Writing headers, status codes, cookies, and character/binary streams.

5. **Session Handling & Event Handling**:
   - `HttpSession`: Session tracking, expiration, timeout, and attributes across user requests.
   - `ServletContextListener` (`AppLifecycleListener`): Reacts to application startup and shutdown.
   - `HttpSessionListener` (`SessionActivityListener`): Tracks active session counters across JVM instances.

6. **Filter Architecture**:
   - `AuditLoggingFilter`: Demonstrates `Filter`, `FilterChain`, request pre-processing, header enrichment, and post-execution response timing.

### Build and Deployment

```bash
# Compile and package as WAR:
mvn clean package

# Deploy the generated WAR target/JavaServletMastery-1.0-SNAPSHOT.war 
# into Apache Tomcat, Jetty, WildFly, or GlassFish webapps directory.
```
