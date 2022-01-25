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
    private ProgressDialog asyncDialog;
    private String personID;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        asyncDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_DARK);
//        //asyncDialog = new ProgressDialog(context, android.R.style.Theme_Holo);
//
//
//        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //막대형태의 ProgressDialog 스타일 설정
//        //asyncDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        asyncDialog.setMessage(context.getResources().getString(R.string.txt_39));
//        asyncDialog.setCancelable(false);
//        asyncDialog.setCanceledOnTouchOutside(false); //ProgressDialog가 진행되는 동안 dialog의 바깥쪽을 눌러 종료하는 것을 금지

        // show dialog
//        asyncDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            connection = DriverManager.getConnection(Constants.DATABASE_CONNECTION_URL);//ConnectionSingleton.getConnection();
            //if(connection == null) { resultMessage = "Check Your Internet Access!"; }
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
        Log.d("============", resultMessage);
        return personID;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
//        if (asyncDialog != null && asyncDialog.isShowing()) {
//            asyncDialog.dismiss();
//        }
//        asyncDialog = null;
    }
}
