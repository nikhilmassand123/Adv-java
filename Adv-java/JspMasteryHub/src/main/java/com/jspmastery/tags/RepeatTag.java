package com.jspmastery.tags;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.TagSupport;

/**
 * Classic Tag Extension API: TagSupport & IterationTag
 * Demonstrates doStartTag(), doAfterBody(), and doEndTag() lifecycles.
 */
public class RepeatTag extends TagSupport {
    private int count;
    private int currentIteration = 0;

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int doStartTag() throws JspException {
        if (count > 0) {
            currentIteration = 1;
            return EVAL_BODY_INCLUDE; // Evaluate body content
        }
        return SKIP_BODY;
    }

    @Override
    public int doAfterBody() throws JspException {
        if (currentIteration < count) {
            currentIteration++;
            return EVAL_BODY_AGAIN; // Re-evaluate tag body
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        currentIteration = 0; // Reset
        return EVAL_PAGE; // Continue rendering the rest of the JSP
    }
}
