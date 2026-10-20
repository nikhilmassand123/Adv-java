package com.jspmastery.tags;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.JspFragment;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Tag Extension API: SimpleTagSupport
 * Demonstrates attributes, dynamic body evaluation, and JSP Fragment handling.
 */
public class CardTag extends SimpleTagSupport {
    private String title;
    private String color = "#0056b3";

    public void setTitle(String title) {
        this.title = title;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();

        out.println("<div style='border: 1px solid #ddd; border-left: 5px solid " + color + "; " +
                    "border-radius: 6px; padding: 15px; margin: 15px 0; background-color: #fff; box-shadow: 0 2px 4px rgba(0,0,0,0.05);'>");
        if (title != null) {
            out.println("<h3 style='margin-top:0; color: " + color + ";'>" + title + "</h3>");
        }

        // JSP Fragment handling: capture or invoke body fragment
        JspFragment body = getJspBody();
        if (body != null) {
            StringWriter sw = new StringWriter();
            body.invoke(sw); // Invoke body into a buffer or directly to JspWriter
            out.println("<div class='card-body'>" + sw.toString() + "</div>");
        }

        out.println("</div>");
    }
}
