package com.example.imagincup.back;

import android.os.AsyncTask;
import android.util.Log;

import com.example.imagincup.Constants;
import com.example.imagincup.back.DTO.DTOPerson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonTableAsyncTask extends AsyncTask<String,Void,String> {
    private String resultMessage = "";
    private Connection connection = null;
    private Statement statement;
    private ResultSet resultSet;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            connection = ConnectionSingleton.getConnection();
            //if(connection == null) { resultMessage = "Check Your Internet Access!"; }
            statement = connection.createStatement();
            resultSet = statement.executeQuery(Constants.QUERY_SELECT_PERSON_DATABASE);
            while (resultSet.next()){
                if(params[0].equals(resultSet.getString("PersonDevice"))){
                    //resultMessage = Constants.DATABASE_EXIST;
                    new InsertPersonData(resultSet);
                    ConnectionSingleton.close();
                    //return Constants.DATABASE_EXIST;
                }
            }
        }
        catch(Exception exception){
            resultMessage = exception.getMessage();
        }
        finally {
            // 시간 나면 코드 클래스로
            if (resultSet != null) try { resultSet.close(); } catch(SQLException ex) {}
            if (statement != null) try { statement.close(); } catch(SQLException ex) {}
            try { ConnectionSingleton.close(); connection.close();} catch(SQLException ex) {}
        }
        Log.d("=================================", resultMessage);
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

class InsertPersonData {

    InsertPersonData(ResultSet resultSet) throws SQLException {
        new DTOPerson(resultSet.getInt("PersonId"), resultSet.getString("PersonName"), resultSet.getInt("PersonDepressionScore"), resultSet.getString("PersonDevice"), resultSet.getInt("RecordID"));
//        dtoPerson.setPersonId(resultSet.getInt("PersonId"));
//        dtoPerson.setPersonName(resultSet.getString("PersonName"));
//        dtoPerson.setPersonDepressionScore(resultSet.getInt("PersonDepressionScore"));
//        dtoPerson.setPersonDevice(resultSet.getString("PersonDevice"));
//        dtoPerson.setRecordId(resultSet.getInt("RecordID"));
    }
}
