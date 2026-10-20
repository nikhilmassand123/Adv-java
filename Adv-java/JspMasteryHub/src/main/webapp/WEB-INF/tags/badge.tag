<%@ tag language="java" pageEncoding="UTF-8" description="Render stylized status badge" %>
<%@ attribute name="type" required="false" %>
<%@ attribute name="text" required="true" %>

<%
    String bg = "#6c757d";
    if ("success".equalsIgnoreCase(type)) bg = "#28a745";
    else if ("danger".equalsIgnoreCase(type)) bg = "#dc3545";
    else if ("info".equalsIgnoreCase(type)) bg = "#17a2b8";
%>

<span style="display:inline-block; padding: 4px 8px; border-radius: 4px; color: #fff; background-color: <%= bg %>; font-size: 12px; font-weight: bold;">
    ${text}
</span>
