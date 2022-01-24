package com.example.imagincup.back.task.person;

import android.util.Log;

import com.example.imagincup.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteRecordThread extends Thread {

    private Integer personID;
    private String query;

    private Connection connection;
    private int resultSet;
    private PreparedStatement preparedStatement;

    public DeleteRecordThread(Integer personID) {
        this.personID = personID;
    }

    @Override
    public void run() {
        try {
            connection = DriverManager.getConnection(Constants.DATABASE_CONNECTION_URL);

            query = "DELETE FROM Record WHERE PersonID = '" + personID + "';";

            preparedStatement = connection.prepareStatement(query);

            resultSet = preparedStatement.executeUpdate();
        }
        catch(Exception exception){
            Log.d("error", String.valueOf(resultSet));
            Log.d("error", String.valueOf(exception));
        }
        finally {
            if (preparedStatement != null) {try { preparedStatement.close(); } catch(SQLException ex) {}}
            if (connection != null) try { connection.close(); } catch(SQLException ex) {}
        }
    }
}
