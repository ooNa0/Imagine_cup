package com.example.imagincup.back;

import android.os.StrictMode;

import com.example.imagincup.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {
    private static Connection connection = null;
    private ConnectionSingleton(){}

    public static Connection getConnection(){
        if(connection != null){
            return connection;
        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(Constants.JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.DATABASE_CONNECTION_URL);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            return connection;
        }
    }
    public static void close() throws SQLException {
        if(connection != null) { if(!connection.isClosed()){ connection.close(); }}
    }
}
