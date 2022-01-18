package com.example.imagincup.back.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.imagincup.back.ConnectionSingleton;
import com.example.imagincup.back.DTO.DTOPerson;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.util.Date;
import java.util.List;

public class InsertRecordDataAsyncTask extends AsyncTask<String,Void,String> {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private int resultSet;
    private String resultMessage = "";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            connection = ConnectionSingleton.getConnection();
            preparedStatement = connection.prepareStatement("insert into Record(PersonID, Date, Question, QuestionID, Answer, Mission, Emotion) values (?, ?, ?, ?, ?, ?, ?);");

            Date sqlDate = new java.sql.Date(System.currentTimeMillis());
            preparedStatement.setInt(1, Integer.valueOf(params[0])); // person id
            preparedStatement.setDate(2, sqlDate); // 시간
            preparedStatement.setString(3, "QuestionQuestion");
            preparedStatement.setInt(4, 1); // 질문 번호
            preparedStatement.setString(5, params[1]); // 대답
            preparedStatement.setString(6, "-"); // 미션
            preparedStatement.setString(7, params[2]); // 감정

            resultSet = preparedStatement.executeUpdate();
        }
        catch(Exception exception){
            resultMessage = exception.getMessage();
        }
        finally {
            if(preparedStatement != null){try { preparedStatement.close(); } catch(SQLException ex) {}}
            try { ConnectionSingleton.close(); } catch(SQLException ex) {}
        }
        Log.d("============", resultMessage);
        return resultMessage;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
