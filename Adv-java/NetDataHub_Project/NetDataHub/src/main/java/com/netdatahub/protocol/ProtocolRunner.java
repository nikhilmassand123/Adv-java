package com.netdatahub.protocol;

import java.net.URL;
import java.net.URLStreamHandlerFactory;

public class ProtocolRunner {
    public static void main(String[] args) throws Exception {
        // Register custom URLStreamHandlerFactory for "netdb://" scheme
        URL.setURLStreamHandlerFactory(new URLStreamHandlerFactory() {
            @Override
            public java.net.URLStreamHandler createURLStreamHandler(String protocol) {
                if ("netdb".equalsIgnoreCase(protocol)) {
                    return new NetDbURLHandler();
                }
                return null;
            }
        });

        System.out.println("[ProtocolRunner] Sending URL request via custom handler: netdb://localhost:9090/query?action=LIST");

        URL customUrl = new URL("netdb://localhost:9090/query?action=LIST");

        CustomContentHandler contentHandler = new CustomContentHandler();
        Object content = contentHandler.getContent(customUrl.openConnection());

        System.out.println("\n--- Received Object via ContentHandler ---");
        System.out.println(content);
    }
}
