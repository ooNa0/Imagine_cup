package com.example.imagincup.back.task.person;

import android.util.Log;

import com.example.imagincup.Constants;
import com.example.imagincup.activity.survey.Survey;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DeletePersonThread extends Thread {

    private Integer personID;
    private String query;

    private Connection connection;
    private int resultSet;
    private PreparedStatement preparedStatement;

    public DeletePersonThread(Integer personID) {
        this.personID = personID;
    }

    @Override
    public void run() {
        Log.d("?????????????2132132\n1?????????\n??????2q3????", String.valueOf(personID));

        try {
            connection = DriverManager.getConnection(Constants.DATABASE_CONNECTION_URL);

//            query = "DELETE p, rc FROM Person AS p" +
//                    " LEFT JOIN Record AS rc ON p.PersonID = rc.PersonID" +
//                    " WHERE p.PersonID =" + personID;

            query = "DELETE FROM Person WHERE PersonID = '" + personID + "';";

            Log.d("?????????????000000???????????????????", query);
            preparedStatement = connection.prepareStatement(query);
            Log.d("????????????????????????????????", query);

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
