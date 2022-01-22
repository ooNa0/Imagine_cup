package com.example.imagincup.back.task.answer;

import android.util.Log;

import com.example.imagincup.Constants;
import com.example.imagincup.back.DTO.DTORecord;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SelectAnswerExistThread extends Thread {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private String PersonId;
    private String query;

    private DTORecord dtoRecord;

    private String date;

    public SelectAnswerExistThread(String personID, DTORecord dtoRecord) {
        this.PersonId = personID;
        this.dtoRecord = dtoRecord;
    }

    @Override
    public void run() {
        try {
            date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

            connection = DriverManager.getConnection(Constants.DATABASE_CONNECTION_URL);
            statement = connection.createStatement();
            query = "SELECT * FROM Record WHERE PersonID='"
                    + PersonId + "' AND Date='" + date + "';";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                dtoRecord = new DTORecord(
                        resultSet.getInt("PersonID"), resultSet.getString("Question"),
                        resultSet.getInt("QuestionID"), resultSet.getString("Mission"),
                        resultSet.getString("Answer"), resultSet.getString("Emotion"),
                        resultSet.getDate("Date"), resultSet.getFloat("EmotionScore"));
            }
        }
        catch(Exception exception){
            Log.d("error", exception.getMessage());
        }
        finally {
            if (resultSet != null) try { resultSet.close(); } catch(SQLException ex) {}
            if (statement != null) try { statement.close(); } catch(SQLException ex) {}
            if (connection != null) try { connection.close(); } catch(SQLException ex) {}
        }
    }
    public DTORecord getdtoRecord(){
        return this.dtoRecord;
    }
}
