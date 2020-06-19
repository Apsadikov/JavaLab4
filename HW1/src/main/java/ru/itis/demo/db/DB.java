package ru.itis.demo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static Connection connection;

    private DB() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/notebook?" +
                        "user=root&" +
                        "password=1234&" +
                        "useUnicode=true&" +
                        "serverTimezone=UTC");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failed to connect to database");
            }
        }
        return connection;
    }
}
