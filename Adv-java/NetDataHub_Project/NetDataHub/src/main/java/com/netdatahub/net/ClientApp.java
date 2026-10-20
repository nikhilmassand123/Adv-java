package com.netdatahub.net;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {
    private static final String HOST = "localhost";
    private static final int PORT = 9090;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("[Client] Connected to NetDataHub server.");
            System.out.println("Available commands: LIST, ADD <name> <price> <qty>, EXIT");

            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine();
                if (input.trim().isEmpty()) continue;

                out.println(input);

                if (input.equalsIgnoreCase("EXIT")) {
                    break;
                }

                if (input.equalsIgnoreCase("LIST")) {
                    String totalLine = in.readLine();
                    System.out.println(totalLine);
                    int count = Integer.parseInt(totalLine.replace("TOTAL ", ""));
                    for (int i = 0; i < count; i++) {
                        System.out.println("  " + in.readLine());
                    }
                } else {
                    String response = in.readLine();
                    System.out.println("[Response] " + response);
                }
            }
        } catch (IOException e) {
            System.err.println("[Client] Error: " + e.getMessage());
        }
    }
}
