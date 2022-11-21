package com.example.imagincup.back.task.record;

import android.util.Log;

import com.example.imagincup.Constants;
import com.example.imagincup.back.DTO.DTORecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// day의 record의 값을 가져옵니다.
public class SelectRecordDayThread extends Thread {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private String PersonId;

    private String selectYear;
    private String selectMonth;
    private String startDate;
    private String endDate;

    private List<DTORecord> listRecord;

    private Date date;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMMM-dd", Locale.US);
    private SimpleDateFormat dateFormatInteger = new SimpleDateFormat("yyyyMMdd");

    public SelectRecordDayThread(String personID, List<DTORecord> listRecord, String selectYear, String selectMonth) {
        this.PersonId = personID;
        this.listRecord = listRecord;
        this.selectMonth = selectMonth;
        this.selectYear = selectYear;
    }

    @Override
    public void run() {
        try {
            Calendar cal = Calendar.getInstance();
            date = dateFormat.parse(selectYear + "-" +selectMonth + "-" + "00");

            cal.setTime(date);

            startDate = dateFormatInteger.format(cal.getTime());
            cal.add(Calendar.MONTH, 1);
            endDate = dateFormatInteger.format(cal.getTime());// selectYear + "-" + String.valueOf(Integer.parseInt(dateFormatInteger.format(date))-1);

            connection = DriverManager.getConnection(Constants.DATABASE_CONNECTION_URL);
            statement = connection.createStatement();
            String query = "SELECT * FROM Record WHERE PersonID='"
                    + PersonId
                    + "' AND Date between '" + startDate + "' AND '"
                    + endDate + "';";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                listRecord.add(new DTORecord(
                        resultSet.getInt("PersonID"), resultSet.getString("Question"),
                        resultSet.getInt("QuestionID"), resultSet.getString("Mission"),
                        resultSet.getString("Answer"), resultSet.getString("Emotion"),
                        resultSet.getDate("Date"), resultSet.getFloat("EmotionScore")));
            }
        }
        catch(Exception exception){
            //Log.d("error", exception.getMessage());
        }
        finally {
            if (resultSet != null) try { resultSet.close(); } catch(SQLException ex) {}
            if (statement != null) try { statement.close(); } catch(SQLException ex) {}
            if (connection != null) try { connection.close(); } catch(SQLException ex) {}
        }
    }
    public List<DTORecord> getlistRecord(){
        return this.listRecord;
    }
}
