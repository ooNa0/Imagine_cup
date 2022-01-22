package com.example.imagincup.back.task.answer;

import android.util.Log;

import com.example.imagincup.Constants;
import com.example.imagincup.back.DTO.DTOQuestion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateSurveyScoreThread extends Thread {

    private Connection connection;
    private PreparedStatement preparedStatement;

    private Integer personID;
    private Integer surveyID;
    private Integer score;

    private DTOQuestion dtoQuestion;
    private String query;

    private String date;

    public UpdateSurveyScoreThread(Integer personID, Integer surveyID, Integer score) {
        this.personID = personID;
        this.surveyID = surveyID;
        this.score = score;
    }

    @Override
    public void run() {
        try {
            connection = DriverManager.getConnection(Constants.DATABASE_CONNECTION_URL);

            query = "UPDATE Score SET ScoreNumber='" + score + "' WHERE QuestionID='"
                    + surveyID + "' AND PersonID='" + personID + "';";

            Log.d("??????????DWQwqEWQEQWEWQEqw", query);
            Log.d("??????????DWQwqEWQ2312321321312EQWEWQEqw", String.valueOf(score));

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.executeUpdate();
        }
        catch(Exception exception){
            Log.d("error11111111111111111111111111", exception.getMessage());
        }
        finally {
            if (preparedStatement != null) {try { preparedStatement.close(); } catch(SQLException ex) {}}
            if (connection != null) try { connection.close(); } catch(SQLException ex) {}
        }
    }
    public DTOQuestion getdtoQuestion(){
        return this.dtoQuestion;
    }
}
