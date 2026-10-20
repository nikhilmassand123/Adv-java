# JSP Architecture, Tag Extensions & JSTL Enterprise Guide

This project is a complete reference implementation demonstrating the full spectrum of JavaServer Pages (JSP) concepts:

### Key Concepts Covered:

1. **JSP Architecture & Translation Process:**
   - How `.jsp` source text is parsed, translated into an underlying Java Servlet class (`_jspService`), compiled to bytecode, and loaded into JVM memory.
2. **JSP Page Lifecycle:**
   - `jspInit()`: Initialization callback.
   - `_jspService(HttpServletRequest, HttpServletResponse)`: Request handling lifecycle method.
   - `jspDestroy()`: Cleanup and termination.
3. **JSP Scripting Elements:**
   - **Directives** (`<%@ page %>`, `<%@ taglib %>`, `<%@ include %>`): Page-level settings and library imports.
   - **Declarations** (`<%! ... %>`): Method and instance variable declarations.
   - **Scriptlets** (`<% ... %>`): Java statement blocks embedded directly inside `_jspService`.
   - **Expressions** (`<%= ... %>`): Expressions evaluated and printed to the output stream.
4. **Expression Language (EL):**
   - `${...}` syntax, scoped variable access (`pageScope`, `requestScope`, `sessionScope`, `applicationScope`), and implicit objects (`header`, `param`, `cookie`).
5. **Tag Extension API & Tag Handlers:**
   - `SimpleTagSupport` (`doTag()`) for modern custom tags and `TagSupport` (`doStartTag`, `doAfterBody`, `doEndTag`) for classic iteration tags.
6. **JSP Fragments & Tag Files:**
   - `JspFragment`: Encapsulated dynamic body fragments passed to custom tag handlers.
   - **Tag Files (`.tag`)**: Modular custom tags written declaratively using JSP syntax inside `/WEB-INF/tags/`.
7. **JSTL (JavaServer Pages Standard Tag Library):**
   - **Core Library (`c:`)**: Conditional logic (`<c:if>`, `<c:choose>`), iterative loops (`<c:forEach>`), URL formatting (`<c:url>`).
   - **Functions Library (`fn:`)**: String manipulation (`fn:length`, `fn:toUpperCase`, `fn:contains`).
   - **XML Library (`x:`) & SQL Library (`sql:`) Overview**: Declarative XML parsing (`<x:parse>`, `<x:out>`) and database query execution directly within markup (considered legacy in modern layered architecture).

### Compilation and Execution

```bash
# 1. Compile the project
mvn clean compile

# 2. Launch the JSP container
mvn exec:java -Dexec.mainClass="com.jspmastery.server.JspServerRunner"
```

Open `http://localhost:8080/index.jsp` in your web browser.
