# J2EE Enterprise Architecture & Web Container Demo

This project provides an end-to-end working implementation of enterprise Java concepts:

### Core Concepts Covered:
1. **J2EE Architecture & Platform**: Multi-tier component model separating Presentation, Business Logic, and Enterprise Information Systems (EIS).
2. **N-Tier Application Concepts**:
   - **Client Tier**: Web Browser issuing standard HTTP requests.
   - **Web Tier**: Web Container parsing HTTP, managing Servlet lifecycles (`init`, `service`, `destroy`).
   - **Business Tier**: `BankingService` handling transaction boundaries and business logic.
   - **EIS / Data Tier**: Data layer (`Account` storage) simulating transactional relational storage.
3. **HTTP Protocol**: Implementation of HTTP request parsing (verbs, query strings, headers) and structured HTTP/1.1 response streaming.
4. **Web Container vs. Application Server**:
   - **Web Container**: Houses Servlets/JSPs, handles incoming HTTP requests, maps URLs, manages concurrency.
   - **Application Server**: Encompasses web containers and extends full enterprise support (JTA transactions, EJB, JMS messaging, connection pooling).

### Compilation & Running

```bash
# 1. Compile the project
mvn clean compile

# 2. Run the Web Container & Application Server:
mvn exec:java -Dexec.mainClass="com.j2ee.enterprise.server.WebContainerServer"
```

Once running, navigate to `http://localhost:8080/app` in your browser.
