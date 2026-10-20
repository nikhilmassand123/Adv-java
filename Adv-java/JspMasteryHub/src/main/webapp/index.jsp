<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, java.time.LocalDateTime" %>

<%-- 1. Standard JSTL Core and Functions Libraries --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%-- 2. Custom TLD & Tag Files --%>
<%@ taglib prefix="custom" uri="http://com.jspmastery/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%-- 3. JSP Declaration Element: <%! ... %> (Variables/Methods become class members) --%>
<%!
    private int globalHitCount = 0;

    public String formatServerTime() {
        return LocalDateTime.now().toString();
    }
%>

<%-- 4. JSP Scriptlet Element: <% ... %> (Executes inside _jspService) --%>
<%
    globalHitCount++;
    pageContext.setAttribute("pageScopeVar", "Stored in Page Scope");
    request.setAttribute("reqScopeVar", "Request Attribute: Hello Enterprise");

    List<String> frameworkList = Arrays.asList("Spring Boot", "Jakarta Faces", "Hibernate", "Quarkus");
    request.setAttribute("frameworks", frameworkList);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>JSP Architecture & Comprehensive Feature Showcase</title>
    <style>
        body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; background: #f0f2f5; margin: 30px; color: #333; }
        .container { max-width: 900px; margin: 0 auto; }
        .box { background: #fff; border-radius: 8px; padding: 20px; margin-bottom: 20px; box-shadow: 0 1px 3px rgba(0,0,0,0.1); }
        h1 { color: #1a365d; }
        h2 { color: #2b6cb0; border-bottom: 2px solid #edf2f7; padding-bottom: 6px; }
        code { background: #edf2f7; padding: 2px 6px; border-radius: 4px; color: #c53030; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { text-align: left; padding: 8px 12px; border-bottom: 1px solid #e2e8f0; }
        th { background: #f7fafc; }
    </style>
</head>
<body>
<div class="container">
    <div class="box">
        <h1>JSP & Tag Extension Architecture Showcase</h1>
        <p>This page demonstrates JSP Elements, Lifecycle, EL, Custom Tag Handlers, Tag Files, and JSTL Core & Functions libraries.</p>
    </div>

    <!-- Section 1: JSP Scripting Elements -->
    <div class="box">
        <h2>1. JSP Scripting Elements</h2>
        <p><strong>Declaration Element (&lt;%! ... %&gt;):</strong> Server Initialized Time = <%= formatServerTime() %></p>
        <p><strong>Scriptlet Element (&lt;% ... %&gt;):</strong> Incremented Global Page Hits = <%= globalHitCount %></p>
        <p><strong>Expression Element (&lt;%= ... %&gt;):</strong> Evaluated Expression 10 * 25 = <%= 10 * 25 %></p>
    </div>

    <!-- Section 2: Expression Language (EL) -->
    <div class="box">
        <h2>2. Expression Language (EL: \${...})</h2>
        <p>Page Scope Access: <code>\${pageScope.pageScopeVar}</code> &rarr; <strong>${pageScope.pageScopeVar}</strong></p>
        <p>Request Scope Access: <code>\${requestScope.reqScopeVar}</code> &rarr; <strong>${requestScope.reqScopeVar}</strong></p>
        <p>Implicit Object (Header): Host = <strong>${header["host"]}</strong></p>
        <p>EL Arithmetic & Logic: <code>\${5 > 2 ? '5 is greater' : '2 is greater'}</code> &rarr; <strong>${5 > 2 ? '5 is greater' : '2 is greater'}</strong></p>
    </div>

    <!-- Section 3: JSTL Core & Functions Library -->
    <div class="box">
        <h2>3. JSTL Core & Functions Library</h2>

        <h4>Core Library (&lt;c:if&gt;, &lt;c:forEach&gt;):</h4>
        <ul>
            <c:forEach var="item" items="${frameworks}" varStatus="status">
                <li>
                    Index ${status.index}: <strong>${item}</strong>
                    <c:if test="${status.first}"><span style="color:green;">(First Element)</span></c:if>
                    <c:if test="${status.last}"><span style="color:blue;">(Last Element)</span></c:if>
                </li>
            </c:forEach>
        </ul>

        <h4>Functions Library (fn:toUpperCase, fn:length):</h4>
        <p>Original string: "jakarta enterprise"</p>
        <p>Uppercase: <code>fn:toUpperCase()</code> &rarr; <strong>${fn:toUpperCase('jakarta enterprise')}</strong></p>
        <p>Length: <code>fn:length()</code> &rarr; <strong>${fn:length('jakarta enterprise')} characters</strong></p>
    </div>

    <!-- Section 4: Tag Files (.tag) -->
    <div class="box">
        <h2>4. JSP Tag Files (/WEB-INF/tags/badge.tag)</h2>
        <p>Status badges rendered using declarative Tag Files:</p>
        <tags:badge type="success" text="SERVICE ONLINE" />
        <tags:badge type="danger" text="LATENCY HIGH" />
        <tags:badge type="info" text="CLUSTER NODE 01" />
    </div>

    <!-- Section 5: Custom Tag Extension API & EL Functions -->
    <div class="box">
        <h2>5. Custom Tag Handlers & Custom EL Functions</h2>
        
        <!-- Invoking SimpleTagSupport handler -->
        <custom:card title="SimpleTag Card Component" color="#2b6cb0">
            This body content was passed into <code>SimpleTagSupport</code> as a <strong>JspFragment</strong>,
            processed by the Java tag handler, and wrapped inside custom markup!
        </custom:card>

        <!-- Invoking Classic IterationTag handler -->
        <p>Repeating content using classic <code>TagSupport</code> loop handler:</p>
        <ul>
            <custom:repeat count="3">
                <li>Echo from RepeatTag iteration!</li>
            </custom:repeat>
        </ul>

        <!-- Invoking Custom EL Functions registered in TLD -->
        <p>Custom EL function <code>custom:reverse('Distributed')</code> &rarr; <strong>${custom:reverse('Distributed')}</strong></p>
        <p>Custom EL function <code>custom:mask('4111222233334444')</code> &rarr; <strong>${custom:mask('4111222233334444')}</strong></p>
    </div>
</div>
</body>
</html>
