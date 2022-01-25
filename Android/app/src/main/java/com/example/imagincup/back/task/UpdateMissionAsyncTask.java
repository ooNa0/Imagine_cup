package com.example.imagincup.back.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.imagincup.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateMissionAsyncTask extends AsyncTask<String,Void,String> {
private Connection connection;
private PreparedStatement preparedStatement;
private String resultMessage = "";
private String query;

@Override
protected void onPreExecute() {
        super.onPreExecute();
        }

@Override
protected String doInBackground(String... params) {
        try {

        connection = DriverManager.getConnection(Constants.DATABASE_CONNECTION_URL);

        query = "UPDATE Record SET Mission='0', Date='" + params[0] + "' WHERE PersonID='" + params[1] + "';";
                Log.d("============1111111111111111111111111", query);

        preparedStatement = connection.prepareStatement(query);

        preparedStatement.executeUpdate();
        }
        catch(Exception exception){
                resultMessage = exception.getMessage();
        }
        finally {
                if(preparedStatement != null){try { preparedStatement.close(); } catch(SQLException ex) {}}
                if (connection != null) try { connection.close(); } catch(SQLException ex) {}
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
