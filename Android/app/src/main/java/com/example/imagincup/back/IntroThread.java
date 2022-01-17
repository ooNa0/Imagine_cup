package com.example.imagincup.back;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.imagincup.Constants;
import com.example.imagincup.back.DTO.DTOPerson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IntroThread extends Thread {

    private String resultMessage = "";
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private ResultSet resultData;
    private String DeviceID;
    private Integer resultStateNumber;

    private DTOPerson dtoPerson;

    public IntroThread(String DeviceID) {
        this.DeviceID = DeviceID;
    }

    @Override
    public void run() {
        resultStateNumber = Constants.RUNNING;
        try {
            connection = ConnectionSingleton.getConnection();
            //if(connection == null) { resultMessage = "Check Your Internet Access!"; }
            statement = connection.createStatement();
            resultSet = statement.executeQuery(Constants.QUERY_SELECT_PERSON_DATABASE);
            while (resultSet.next()){
                if(DeviceID.equals(resultSet.getString("PersonDevice"))){
                    new InsertPersonData(resultSet);
                    resultStateNumber = Constants.DATABASE_EXIST;
                    break;
                }
                else{
                    resultStateNumber = Constants.DATABASE_NOT_EXIST;
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
    }

    public int getResult(){
        return resultStateNumber;
    }
    public DTOPerson getResultDataSet(){
        return dtoPerson;
    }

    class InsertPersonData {
        InsertPersonData(ResultSet resultSet) throws SQLException {
            dtoPerson = new DTOPerson(resultSet.getInt("PersonId"), resultSet.getString("PersonName"), resultSet.getInt("PersonDepressionScore"), resultSet.getString("PersonDevice"), resultSet.getInt("RecordID"));
        }
    }

}