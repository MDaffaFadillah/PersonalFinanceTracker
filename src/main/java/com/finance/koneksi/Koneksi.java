package com.finance.koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/db_finance";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }
}