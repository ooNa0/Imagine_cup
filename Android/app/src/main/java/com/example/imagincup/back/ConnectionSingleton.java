package com.example.imagincup.back;

import android.os.StrictMode;
import android.util.Log;

import com.example.imagincup.Constants;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionSingleton {
    private static Connection connection;
    //private java.util.List statementList = new java.util.ArrayList();;

    private ConnectionSingleton(){}

    public static Connection getConnection(){
        // 싱글톤 나중에.,, Invalid state, the Connection object is closed.
        //if(connection != null){ return connection; }
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

//    public void closeAll() {
//        for (int i = 0 ; i < statementList.size() ; i++) {
//            Statement statement = (Statement)statementList.get(i);
//            try {
//                statement.close();
//            } catch(SQLException ex) {}
//        }
//    }

    public static void close() throws SQLException {
        //closeAll();
        if(connection != null) { if(!connection.isClosed()){ connection.close(); }}
    }

//    public Statement createStatement() throws SQLException {
//        Statement statement = connection.createStatement();
//        statementList.add(statement);
//        return statement;
//    }
//
//    public CallableStatement prepareCall(String sql) throws SQLException {
//        CallableStatement callableStatement = connection.prepareCall(sql);
//        statementList.add(callableStatement);
//        return callableStatement;
//    }
//
//    public PreparedStatement prepareStatement(String sql) throws SQLException {
//        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        statementList.add(preparedStatement);
//        return preparedStatement;
//    }
}
