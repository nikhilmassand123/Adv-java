# Java Enterprise & Distributed Systems Suite

A consolidated architectural reference and execution guide for the enterprise Java modules:
1. **NetDataHub** — Swing GUI, Pluggable Look & Feel, JDBC Database Connectivity, TCP Network Sockets, and Custom URL Protocol/Content Handlers.
2. **JavaDistributedHub** — RMI Distributed Architecture, Naming & Directory Services (JNDI), Object Serialization, and Internationalization (I18N).
3. **J2EE Enterprise Architecture** — Multi-tier Enterprise Architecture, Custom HTTP Protocol Parser/Formatter, and Web Container/Application Server Lifecycle.
4. **JavaServletMastery & JspMasteryHub** — Jakarta Servlet API, Lifecycles, Context vs. Config, Sessions, Listeners, Filters, JSP Elements, Expression Language (EL), Custom Tag Extensions, and JSTL.

---

## Architectural Overview

```
+---------------------------------------------------------------------------------------------------------+
|                                        CLIENT PRESENTATION TIER                                         |
|  - Standalone GUI: Java Swing (JFrame, JTextField, JButton, JRadioButton, Pluggable Look & Feel)        |
|  - Distributed Client: RMI Client Stub Invocations via Registry Lookup                                  |
|  - Web Client: Standard Web Browser issuing HTTP GET/POST queries                                      |
+---------------------------------------------------------------------------------------------------------+
                                                     |
                                                     v
+---------------------------------------------------------------------------------------------------------+
|                                    WEB & PROTOCOL INTERMEDIARY TIER                                     |
|  - Custom URL Stream & Content Handlers: netdb:// protocol decoding & object streaming                  |
|  - Embedded Web Containers (Tomcat/Jetty): Servlet Filters, Listeners, Request/Response Pipelines       |
|  - JSP Engine: Translation to Servlets, Expression Language (EL), Custom Tag Handlers, JSTL Core/Fn     |
+---------------------------------------------------------------------------------------------------------+
                                                     |
                                                     v
+---------------------------------------------------------------------------------------------------------+
|                                         BUSINESS LOGIC TIER                                             |
|  - RMI Server Servant: UnicastRemoteObject handling remote procedure calls over TCP                      |
|  - Banking & Enterprise Services: Session management, transactional validation, and I18N localization   |
|  - Java Naming and Directory Interface (JNDI): SPI routing, naming resolution, directory attributes     |
+---------------------------------------------------------------------------------------------------------+
                                                     |
                                                     v
+---------------------------------------------------------------------------------------------------------+
|                                    DATA & PERSISTENCE TIER (EIS)                                        |
|  - JDBC (java.sql): Connection pooling, Statements, PreparedStatements, and ResultSet metadata         |
|  - Relational Database Engine: In-Memory / Embedded H2 Database instances                              |
|  - Object Serialization: ObjectOutputStream/ObjectInputStream persistence and byte streaming            |
+---------------------------------------------------------------------------------------------------------+
```

---

## Module 1: NetDataHub

### Key Concepts
- **Java Swing & PLAF:** Lightweight GUI built with `javax.swing`. Demonstrates `JTextField`, `JButton`, `JToggleButton`, `JCheckBox`, and `JRadioButton` inside a `ButtonGroup`. Dynamically switches UI themes at runtime via `UIManager.setLookAndFeel()`.
- **Database Programming (`java.sql`):** Connects to relational storage using `DriverManager.getConnection()`, uses `PreparedStatement` with parameterized binding to prevent SQL injection, and analyzes schema dynamic attributes via `ResultSetMetaData`.
- **Network Sockets (`java.net`):** Implements a concurrent TCP server via `ServerSocket` and worker thread pools (`ExecutorService`), paired with a console-driven `Socket` client.
- **Custom Protocol & Content Handlers:** Extends `URLStreamHandler` and `URLConnection` to register an application-specific URL scheme (`netdb://host:port/query?action=list`) and parses raw data into typed domain objects via `ContentHandler`.

### Directory Layout
```
NetDataHub/
├── pom.xml
└── src/main/java/com/netdatahub/
    ├── swing/
    │   └── SwingDemo.java                    # Standalone Swing GUI with PLAF configuration
    ├── db/
    │   └── DatabaseService.java              # JDBC connection, DDL, parameterized CRUD operations
    ├── net/
    │   ├── ServerApp.java                    # Multithreaded TCP ServerSocket server
    │   └── ClientApp.java                    # Interactive client console
    └── protocol/
        ├── CustomContentHandler.java         # ContentHandler implementation converting streams to objects
        ├── NetDbURLHandler.java              # Custom URLStreamHandler and URLConnection for netdb://
        └── ProtocolRunner.java               # URLStreamHandlerFactory registration & invocation runner
```

### Build & Run
```bash
cd NetDataHub
mvn clean compile

# 1. Run Swing Desktop Application:
mvn exec:java -Dexec.mainClass="com.netdatahub.swing.SwingDemo"

# 2. Run Database Server (Terminal 1):
mvn exec:java -Dexec.mainClass="com.netdatahub.net.ServerApp"

# 3. Test Custom Protocol Handler (Terminal 2):
mvn exec:java -Dexec.mainClass="com.netdatahub.protocol.ProtocolRunner"

# 4. Run Interactive Socket Client (Terminal 3):
mvn exec:java -Dexec.mainClass="com.netdatahub.net.ClientApp"
```

---

## Module 2: JavaDistributedHub

### Key Concepts
- **RMI Architecture:** Facilitates cross-JVM method invocation using stubs to marshal and unmarshal parameters and return types over TCP.
- **RMI Registry:** Bootstrap registry service instantiated on port `1099` via `LocateRegistry.createRegistry()`, binding services with `registry.rebind()`.
- **Object Serialization:** Implements `java.io.Serializable` with explicit `serialVersionUID`. Uses the `transient` keyword to shield sensitive values from stream persistence.
- **JNDI (`javax.naming.*`):** Demonstrates unified naming and directory abstraction layer for looking up distributed resources and inspecting directory attributes.
- **Internationalization (I18N):** Dynamically localizes messages, dates, and currency values across various regions (`en_US`, `fr_FR`, `de_DE`, `hi_IN`) using `Locale`, `ResourceBundle`, `NumberFormat`, and `DateFormat`.

### Directory Layout
```
JavaDistributedHub/
├── pom.xml
└── src/main/
    ├── java/com/javadistributed/
    │   ├── model/
    │   │   └── UserProfile.java              # Serializable object with transient attributes
    │   ├── serialization/
    │   │   └── SerializationDemo.java        # Object serialization & deserialization pipeline
    │   ├── rmi/
    │   │   ├── RemoteInventoryService.java   # Remote interface contract (java.rmi.Remote)
    │   │   ├── RemoteInventoryServiceImpl.java # UnicastRemoteObject servant implementation
    │   │   ├── RmiServerApp.java             # Registry setup & service registration
    │   │   └── RmiClientApp.java             # Remote lookup and distributed invocation
    │   ├── jndi/
    │   │   └── JndiNamingDemo.java           # JNDI InitialContext & directory concepts
    │   └── i18n/
    │       └── InternationalizationDemo.java # Multi-locale formatting demonstration
    └── resources/com/javadistributed/i18n/
        ├── messages.properties               # Default bundle
        ├── messages_en_US.properties         # US English translations
        ├── messages_fr_FR.properties         # French translations
        ├── messages_de_DE.properties         # German translations
        └── messages_hi_IN.properties         # Hindi translations
```

### Build & Run
```bash
cd JavaDistributedHub
mvn clean compile

# Serialization Demonstration:
mvn exec:java -Dexec.mainClass="com.javadistributed.serialization.SerializationDemo"

# Internationalization Demonstration:
mvn exec:java -Dexec.mainClass="com.javadistributed.i18n.InternationalizationDemo"

# JNDI Naming & Directory Overview:
mvn exec:java -Dexec.mainClass="com.javadistributed.jndi.JndiNamingDemo"

# Start RMI Server (Terminal 1):
mvn exec:java -Dexec.mainClass="com.javadistributed.rmi.RmiServerApp"

# Start RMI Client (Terminal 2):
mvn exec:java -Dexec.mainClass="com.javadistributed.rmi.RmiClientApp"
```

---

## Module 3: J2EE Enterprise Architecture

### Key Concepts
- **N-Tier Architecture:** Splits concerns across the Client Tier (browser/HTTP client), Web Tier (presentation controllers), Business Tier (domain rules and transactional boundaries), and EIS Tier (data persistence).
- **HTTP Protocol Implementation:** Sockets parse raw HTTP/1.1 request lines, decode header metadata, extract query parameters, and serialize HTTP response codes (`200 OK`, `404 Not Found`) along with content-type payloads.
- **Web Containers vs. Application Servers:** Highlights the distinction between lightweight Servlet engines and full enterprise application servers managing distributed transactions, security, and messaging.

### Directory Layout
```
J2EE_Enterprise_Demo/
├── pom.xml
└── src/main/java/com/j2ee/enterprise/
    ├── model/
    │   └── Account.java                      # Domain entity model (EIS tier data)
    ├── service/
    │   └── BankingService.java               # Business Tier service managing transaction boundaries
    ├── web/
    │   ├── HttpRequest.java                  # HTTP protocol parser (method, path, headers, query params)
    │   ├── HttpResponse.java                 # HTTP response stream formatter (status line, headers, body)
    │   ├── MiniServlet.java                  # Servlet lifecycle contract (init, service, destroy)
    │   └── BankingServlet.java               # Web Tier presentation component
    └── server/
        └── WebContainerServer.java           # Multi-threaded HTTP Web Container & socket dispatcher
```

### Build & Run
```bash
cd J2EE_Enterprise_Demo
mvn clean compile

# Start the Web Container:
mvn exec:java -Dexec.mainClass="com.j2ee.enterprise.server.WebContainerServer"

# Open in Browser:
# http://localhost:8080/app
```

---

## Module 4: Servlets, JSP, Tag Extensions & JSTL

### Key Concepts
- **Servlet Lifecycle & Configuration:** Lifecycle execution sequence (`init` -> `service` -> `destroy`). Explores isolated configuration via `ServletConfig` versus application-wide singletons via `ServletContext`.
- **Session & Event Handling:** State tracking over stateless HTTP using `HttpSession` (`JSESSIONID`). Lifecycle monitoring through `ServletContextListener` and `HttpSessionListener`.
- **Filters:** Chain of Responsibility pattern with `Filter`, `FilterChain`, and `FilterConfig` to perform request auditing, security checks, and response header injection.
- **JSP Architecture & Elements:** Translation of `.jsp` into Java bytecode (`_jspService`). Directives (`<%@ %>`), Declarations (`<%! %>`), Scriptlets (`<% %>`), and Expressions (`<%= %>`).
- **Expression Language (EL) & Tags:** Scoped variable evaluation (`${...}`), Tag Files (`.tag`), classic `TagSupport` iterations, modern `SimpleTagSupport` with `JspFragment`, and JSTL Core/Functions libraries.

### Directory Layout
```
JavaServletMastery/
├── pom.xml
└── src/main/java/com/javaservlet/mastery/
    ├── listeners/
    │   └── AppLifecycleListener.java         # ServletContextListener & HttpSessionListener
    ├── filters/
    │   └── RequestAuditFilter.java           # Intercepting filter with pre/post processing
    ├── servlets/
    │   └── CoreDemoServlet.java              # Lifecycle, config/context, sessions, request/response
    └── ServerRunner.java                     # Embedded server launcher

JspMasteryHub/
├── pom.xml
└── src/main/
    ├── java/com/jspmastery/
    │   ├── functions/
    │   │   └── StringFunctions.java          # Custom EL function implementations
    │   ├── tags/
    │   │   ├── CardTag.java                  # SimpleTagSupport & JspFragment body handler
    │   │   └── RepeatTag.java                # Classic IterationTag (TagSupport)
    │   └── server/
    │       └── JspServerRunner.java          # Embedded Web Container launcher for JSPs
    └── webapp/
        ├── index.jsp                         # Dynamic JSP combining all scripting, EL, & tags
        └── WEB-INF/
            ├── web.xml                       # Deployment descriptor
            ├── tags/
            │   └── badge.tag                 # Declarative JSP Tag File
            └── tlds/
                └── custom-tags.tld           # Tag Library Descriptor for custom tags & EL functions
```

### Build & Run
```bash
# 1. Servlet Application:
cd JavaServletMastery
mvn clean compile
mvn exec:java -Dexec.mainClass="com.javaservlet.mastery.ServerRunner"
# Access via browser: http://localhost:8080/portal

# 2. JSP & Tag Extensions Application:
cd JspMasteryHub
mvn clean compile
mvn exec:java -Dexec.mainClass="com.jspmastery.server.JspServerRunner"
# Access via browser: http://localhost:8080/index.jsp
```

---

## Technology Stack & Prerequisites

- **JDK:** Java SE 17 or higher
- **Build Tool:** Apache Maven 3.8+
- **Database Engine:** Embedded H2 Database (in-memory mode)
- **Web Runtime:** Embedded Jakarta Servlet / Jetty Container