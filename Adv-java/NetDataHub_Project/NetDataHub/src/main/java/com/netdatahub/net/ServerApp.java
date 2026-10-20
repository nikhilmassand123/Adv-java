package com.netdatahub.net;

import com.netdatahub.db.DatabaseService;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApp {
    private static final int PORT = 9090;
    private final DatabaseService dbService;

    public ServerApp() {
        this.dbService = new DatabaseService();
    }

    public void start() {
        ExecutorService pool = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[Server] NetDataHub Server listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[Server] Accepted connection from " + clientSocket.getRemoteSocketAddress());
                pool.execute(new ClientHandler(clientSocket, dbService));
            }
        } catch (IOException e) {
            System.err.println("[Server] Error: " + e.getMessage());
        }
    }

    static class ClientHandler implements Runnable {
        private final Socket socket;
        private final DatabaseService db;

        public ClientHandler(Socket socket, DatabaseService db) {
            this.socket = socket;
            this.db = db;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

                String command;
                while ((command = reader.readLine()) != null) {
                    if (command.equalsIgnoreCase("EXIT")) {
                        writer.println("Connection closed.");
                        break;
                    }

                    if (command.equalsIgnoreCase("LIST")) {
                        List<String> products = db.getAllProducts();
                        writer.println("TOTAL " + products.size());
                        for (String p : products) {
                            writer.println(p);
                        }
                    } else if (command.startsWith("ADD ")) {
                        String[] parts = command.split(" ");
                        if (parts.length == 4) {
                            String name = parts[1];
                            double price = Double.parseDouble(parts[2]);
                            int qty = Integer.parseInt(parts[3]);
                            boolean ok = db.addProduct(name, price, qty);
                            writer.println(ok ? "SUCCESS: Product inserted." : "ERROR: Failed to insert.");
                        } else {
                            writer.println("ERROR: Invalid format. Use: ADD <name> <price> <qty>");
                        }
                    } else {
                        writer.println("ERROR: Unknown Command. Commands: LIST, ADD <name> <price> <qty>, EXIT");
                    }
                }
            } catch (IOException e) {
                System.err.println("[Server] Client disconnected: " + socket.getRemoteSocketAddress());
            } finally {
                try {
                    socket.close();
                } catch (IOException ignored) {}
            }
        }
    }

    public static void main(String[] args) {
        new ServerApp().start();
    }
}
