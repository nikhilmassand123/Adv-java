package com.netdatahub.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {
    // Embedded H2 in-memory DB connection URL
    private static final String JDBC_URL = "jdbc:h2:mem:inventory;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public DatabaseService() {
        initDatabase();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    private void initDatabase() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS products (
                id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(100) NOT NULL,
                price DOUBLE NOT NULL,
                quantity INT NOT NULL
            );
        """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);

            // Populate sample data
            addProduct("Laptop", 1200.50, 10);
            addProduct("Mechanical Keyboard", 89.99, 25);
            addProduct("Wireless Mouse", 29.99, 50);
            System.out.println("[DB] In-memory database initialized with sample records.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean addProduct(String name, double price, int qty) {
        String insertSQL = "INSERT INTO products(name, price, quantity) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, qty);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized List<String> getAllProducts() {
        List<String> results = new ArrayList<>();
        String querySQL = "SELECT id, name, price, quantity FROM products";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(querySQL)) {

            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();

            while (rs.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= cols; i++) {
                    row.append(meta.getColumnName(i)).append(": ").append(rs.getString(i));
                    if (i < cols) row.append(" | ");
                }
                results.add(row.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
