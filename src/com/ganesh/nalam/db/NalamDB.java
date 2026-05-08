package com.ganesh.nalam.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NalamDB {
    private static Connection connection=null;
    private static String USERNAME="root";
    private static String PASSWORD="";
    private static String URL="jdbc:mysql://localhost:3306/nalamDB";

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
