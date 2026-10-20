package com.netdatahub.protocol;

import java.io.*;
import java.net.*;

public class NetDbURLHandler extends URLStreamHandler {

    @Override
    protected URLConnection openConnection(URL u) {
        return new NetDbURLConnection(u);
    }

    static class NetDbURLConnection extends URLConnection {
        private Socket socket;

        public NetDbURLConnection(URL url) {
            super(url);
        }

        @Override
        public void connect() throws IOException {
            if (!connected) {
                int port = (url.getPort() == -1) ? 9090 : url.getPort();
                this.socket = new Socket(url.getHost(), port);
                this.connected = true;
            }
        }

        @Override
        public InputStream getInputStream() throws IOException {
            connect();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String command = "LIST";
            String query = url.getQuery();
            if (query != null && query.contains("action=")) {
                command = query.split("action=")[1];
            }

            out.println(command);
            return socket.getInputStream();
        }

        @Override
        public String getContentType() {
            return "text/product-list";
        }
    }
}
