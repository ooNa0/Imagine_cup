package com.example.imagincup.back.task;

import android.util.Log;

import com.example.imagincup.Constants;
import com.example.imagincup.activity.survey.Survey;
import com.example.imagincup.back.ConnectionSingleton;
import com.example.imagincup.back.DTO.DTOPerson;
import com.example.imagincup.back.IntroThread;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class InsertSurveyThread extends Thread {

    private List<Survey> scoreList;
    private String personID;

    private Connection connection;
    private Statement statement;
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

            preparedStatement = connection.prepareStatement("insert into Score(PersonID, QuestionID, ScoreNumber) values (?, ?, ?)");

//            preparedStatement = connection.prepareStatement("insert into Score(PersonID, QuestionID, ScoreNumber) values" +
//                    "(?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?)" +
//                    "(?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?)" +
//                    "(?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?)" +
//                    "(?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?);");
//
//            "insert into Record(PersonID, Date, Question, QuestionID, Answer, Mission, Emotion) values (?, ?, ?, ?, ?, ?, ?);"

            quesitionID = 0;
            for(Survey survey : scoreList){
                quesitionID++;
                preparedStatement.setInt(1, Integer.parseInt(personID)); // person id
                preparedStatement.setInt(2, quesitionID);
                Log.d("??????????????????1111111111???????????????????????", "22");
//                Log.d("??????????????????1111111111???????????????????????", String.valueOf(survey));
//                Log.d("??????????????????1111111111???????????????????????", String.valueOf(survey.getBtn1()));
//                Log.d("??????????????????1111111111???????????????????????", String.valueOf(survey.getBtn1().isChecked()));
//                Log.d("?????????????????22222222222222????????????????????????", String.valueOf(survey.getBtn2().isChecked()));
//                Log.d("???????????2333333333333333??????????????????????????????", String.valueOf(survey.getBtn3().isChecked()));
//                Log.d("??????????????????????????44444444444444???????????????", String.valueOf(survey.getBtn4().isChecked()));
                preparedStatement.setString(3, String.valueOf(survey.getScore()));
                resultSet = preparedStatement.executeUpdate();
            }
        }
        catch(Exception exception){
            Log.d("error", exception.getMessage());
        }
        finally {
            if (preparedStatement != null) {try { preparedStatement.close(); } catch(SQLException ex) {}}
            if (connection != null) try { connection.close(); } catch(SQLException ex) {}
        }
    }

    class InsertPersonData {
        InsertPersonData(ResultSet resultSet) throws SQLException {
            //dtoPerson = new DTOPerson(resultSet.getInt("PersonId"), resultSet.getString("PersonName"), resultSet.getInt("PersonDepressionScore"), resultSet.getString("PersonDevice"), resultSet.getInt("RecordID"));
        }
    }
}
