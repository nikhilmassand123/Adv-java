package com.servletexample.listeners;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Event Handling: ServletContextListener
 * Listens to startup and shutdown events of the web application.
 */
@WebListener
public class AppLifecycleListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        // Initialize an application-wide active user counter
        context.setAttribute("activeSessionsCounter", new AtomicInteger(0));
        System.out.println("[WebListener] ServletContext Initialized for: " + context.getContextPath());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[WebListener] ServletContext Destroyed. Cleaning up application resources.");
    }
}
