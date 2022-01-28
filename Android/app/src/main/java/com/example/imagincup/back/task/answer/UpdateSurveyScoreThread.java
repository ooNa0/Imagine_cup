package com.example.imagincup.back.task.answer;
import com.example.imagincup.Constants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateSurveyScoreThread extends Thread {

    private Connection connection;
    private PreparedStatement preparedStatement;

    private Integer personID;
    private Integer surveyID;
    private Integer score;

    private String query;

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

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.executeUpdate();
        }
        catch(Exception exception){
        }
        finally {
            if (preparedStatement != null) {try { preparedStatement.close(); } catch(SQLException ex) {}}
            if (connection != null) try { connection.close(); } catch(SQLException ex) {}
        }
    }
}
