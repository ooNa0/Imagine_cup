package com.example.imagincup.back.task.answer;

import android.util.Log;

import com.example.imagincup.Constants;
import com.example.imagincup.back.DTO.DTOQuestion;
import com.example.imagincup.back.DTO.DTORecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SelectQuestionTextThread extends Thread {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private Integer surveyID;
    private Integer questionID;
    private String questionText;

    private DTOQuestion dtoQuestion;
    private String query;

    private String date;

    public SelectQuestionTextThread(Integer questionID, Integer surveyID, String questionText) {
        this.questionID = questionID;
        this.surveyID = surveyID;
        this.questionText = questionText;
    }

    @Override
    public void run() {
        try {
            connection = DriverManager.getConnection(Constants.DATABASE_CONNECTION_URL);
            statement = connection.createStatement();
            query = "SELECT * FROM Question WHERE QuestionID='"
                    + questionID + "';";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                dtoQuestion = new DTOQuestion(
                        resultSet.getInt("QuestionID"), resultSet.getString("QuestionText"),
                        resultSet.getInt("SurveyID"));
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
    public DTOQuestion getdtoQuestion(){
        return this.dtoQuestion;
    }
}
