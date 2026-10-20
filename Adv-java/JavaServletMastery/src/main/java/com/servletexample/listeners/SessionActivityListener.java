package com.servletexample.listeners;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Event Handling: HttpSessionListener
 * Listens to session creation and destruction events.
 */
@WebListener
public class SessionActivityListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        ServletContext context = se.getSession().getServletContext();
        AtomicInteger counter = (AtomicInteger) context.getAttribute("activeSessionsCounter");
        if (counter != null) {
            int current = counter.incrementAndGet();
            System.out.println("[SessionListener] New Session Created: " + se.getSession().getId() + " | Active Count: " + current);
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext context = se.getSession().getServletContext();
        AtomicInteger counter = (AtomicInteger) context.getAttribute("activeSessionsCounter");
        if (counter != null) {
            int current = counter.decrementAndGet();
            System.out.println("[SessionListener] Session Expired/Invalidated: " + se.getSession().getId() + " | Active Count: " + current);
        }
    }
}
