package com.example.imagincup.back;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AzureAsyncTask extends AsyncTask<String,Void,String> {
    //https://www.youtube.com/watch?v=WJBs0zKGqH0
    private String message = "";
    private Connection connection = null;
    private Boolean isSuccess = false;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            connection = ConnectionSingleton.getConnection();
            if(connection == null) message = "Check Yout Internet Access!";
            else{
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from Person");
                if(resultSet.next()){
                    //name1 = resultSet.getString("PersonName");
                    message = "query successful";
                    isSuccess = true;
                    connection.close();
                }
                else{
                    message = "invalid Query";
                    isSuccess = false;
                }
            }
        }
        catch(Exception exception){
            isSuccess = false;
            message = exception.getMessage();
        }
        Log.d("aaaa", message);
        return message;
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
