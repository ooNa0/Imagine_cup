package com.example.imagincup.back.task;

import com.example.imagincup.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SumDepressionThread  extends Thread {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private Integer PersonId;

    private Integer sumScore;

    public SumDepressionThread(Integer PersonId){
        this.PersonId = PersonId;
    }

    @Override
    public void run() {
        try {
            connection = DriverManager.getConnection(Constants.DATABASE_CONNECTION_URL);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT ScoreNumber FROM Score WHERE PersonID = '"+ PersonId +"'");

            sumScore = 0;
            while (resultSet.next()){
                sumScore += resultSet.getInt("ScoreNumber");
            }

        }
        catch(Exception exception){
        }
        finally {
            if (resultSet != null) try { resultSet.close(); } catch(SQLException ex) {}
            if (statement != null) try { statement.close(); } catch(SQLException ex) {}
            if (connection != null) try { connection.close(); } catch(SQLException ex) {}
        }
    }

    public int getSumScore(){
        return this.sumScore;
    }
}
