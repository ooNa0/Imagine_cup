package com.example.imagincup.back.task;

import com.example.imagincup.Constants;
import com.example.imagincup.activity.survey.Survey;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InsertSurveyThread extends Thread {

    private List<Survey> scoreList;
    private String personID;

    private Connection connection;
    private int resultSet;
    private PreparedStatement preparedStatement;

    private Integer quesitionID;

    public InsertSurveyThread(String personID, List<Survey> scoreList) {
        this.personID = personID;
        this.scoreList = scoreList;
    }

    @Override
    public void run() {
        try {
            connection = DriverManager.getConnection(Constants.DATABASE_CONNECTION_URL);
            preparedStatement = connection.prepareStatement("insert into Score(PersonID, QuestionID, ScoreNumber) values" +
                    "(?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?)," +
                    "(?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?)," +
                    "(?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?)," +
                    "(?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?);");

            quesitionID = 0;
            for(Survey survey : scoreList){
                preparedStatement.setInt(1 + quesitionID*3, Integer.parseInt(personID)); // person id
                preparedStatement.setInt(2 + quesitionID*3, quesitionID+1);
                preparedStatement.setString(3 + quesitionID*3, String.valueOf(survey.getScore()));
                quesitionID++;
            }
            resultSet = preparedStatement.executeUpdate();
        }
        catch(Exception exception){
        }
        finally {
            if (preparedStatement != null) {try { preparedStatement.close(); } catch(SQLException ex) {}}
            if (connection != null) try { connection.close(); } catch(SQLException ex) {}
        }
    }
}
