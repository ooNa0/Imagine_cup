package com.example.imagincup.back.task.answer;

import android.os.AsyncTask;
import android.util.Log;

import com.example.imagincup.Constants;
import com.example.imagincup.back.ConnectionSingleton;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdatePersonAsyncTask extends AsyncTask<Integer,Void,String> {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private String resultMessage = "";
    private String query;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Integer... params) {
        try {

            connection = DriverManager.getConnection(Constants.DATABASE_CONNECTION_URL);

            query = "UPDATE Person SET PersonDepressionScore='" + params[0] + "', QuestionID='"
                    + params[1] + "' WHERE PersonID='" + params[2] + "';";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.executeUpdate();
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
