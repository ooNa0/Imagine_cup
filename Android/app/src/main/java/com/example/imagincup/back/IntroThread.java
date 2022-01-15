package com.example.imagincup.back;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;

import com.example.imagincup.Constants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IntroThread extends Thread {

    private String resultMessage = "";
    private Connection connection = null;
    private Statement statement;
    private ResultSet resultSet;
    private String DeviceID;
    private Message message;

    private Handler handler;

    public IntroThread(Handler handler, String DeviceID) {
        this.handler = handler;
        this.DeviceID = DeviceID;
    }

    @Override
    public void run() {

        message = new Message();
        message.what = Constants.RUNNING;
        try {
            //Thread.sleep(6000);
            connection = ConnectionSingleton.getConnection();
            //if(connection == null) { resultMessage = "Check Your Internet Access!"; }
            statement = connection.createStatement();
            resultSet = statement.executeQuery(Constants.QUERY_SELECT_PERSON_DATABASE);
            while (resultSet.next()){
                if(DeviceID.equals(resultSet.getString("PersonDevice"))){
                    //resultMessage = Constants.DATABASE_EXIST;
                    new InsertPersonData(resultSet);
                    //ConnectionSingleton.close();
                    message.what = Constants.DATABASE_EXIST;
                    handler.sendEmptyMessage(message.what);
                    Thread.currentThread().interrupt();
                    break;
                }
                else{
                    message.what = Constants.DATABASE_NOT_EXIST;
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
            try { ConnectionSingleton.close();} catch(SQLException ex) {}
        }
        handler.sendEmptyMessage(message.what);
        Thread.currentThread().interrupt();
        Log.d("=================================", String.valueOf(message.what));
        Log.d("-------------------", resultMessage);
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
}