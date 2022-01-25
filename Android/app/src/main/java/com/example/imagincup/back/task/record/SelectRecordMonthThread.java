package com.example.imagincup.back.task.record;

import android.util.Log;

import com.example.imagincup.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectRecordMonthThread extends Thread {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private String PersonId;

    private String startDate;
    private String endDate;

    private Float resultAverage;
    public SelectRecordMonthThread(String personID, String startDate, String endDate) {
        this.PersonId = personID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public void run() {
        try {
            connection = DriverManager.getConnection(Constants.DATABASE_CONNECTION_URL);
            statement = connection.createStatement();
            String query = "SELECT AVG(DepressionScore) FROM Record WHERE PersonID='"
                    + PersonId
                    + "' AND Date between '" + startDate + "' AND '"
                    + endDate + "';";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                resultAverage = resultSet.getFloat(1);

            }
        }
        catch(Exception exception){
            Log.d("error", exception.getMessage());
        }
        finally {
            // 시간 나면 코드 클래스로
            if (resultSet != null) try { resultSet.close(); } catch(SQLException ex) {}
            if (statement != null) try { statement.close(); } catch(SQLException ex) {}
            if (connection != null) try { connection.close(); } catch(SQLException ex) {}
        }
    }

    public Float getResultAverage(){
        return resultAverage;
    }
}

