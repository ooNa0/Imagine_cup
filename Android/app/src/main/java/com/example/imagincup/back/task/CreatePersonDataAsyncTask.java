package com.example.imagincup.back;

import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

import com.example.imagincup.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreatePersonDataAsyncTask  extends AsyncTask<String,Void,String> {
    private String resultMessage = "";
    private Connection connection;
    private int resultSet;
    private PreparedStatement preparedStatement;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            connection = ConnectionSingleton.getConnection();
            //if(connection == null) { resultMessage = "Check Your Internet Access!"; }
            preparedStatement = connection.prepareStatement("insert into Person(PersonName, PersonDepressionScore, PersonDevice) values (?, ?, ?);");

            preparedStatement.setString(1, params[0]);
            preparedStatement.setInt(2, 1);
            preparedStatement.setString(3, params[1]);

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
