package com.example.imagincup.back.task;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.imagincup.Constants;
import com.example.imagincup.back.ConnectionSingleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreatePersonDataAsyncTask extends AsyncTask<String,Void,String> {
    private String resultMessage = "";
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String personID;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            connection = DriverManager.getConnection(Constants.DATABASE_CONNECTION_URL);
            preparedStatement = connection.prepareStatement("insert into Person(PersonName, PersonDepressionScore, PersonDevice) values (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, params[0]);
            preparedStatement.setInt(2, 0);
            preparedStatement.setString(3, params[1]);

            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            personID =  String.valueOf(resultSet.getLong(1));
        }
        catch(Exception exception){
            resultMessage = exception.getMessage();
        }
        finally {
            if(preparedStatement != null){try { preparedStatement.close(); } catch(SQLException ex) {}}
            try { ConnectionSingleton.close(); } catch(SQLException ex) {}
        }
        return personID;
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
