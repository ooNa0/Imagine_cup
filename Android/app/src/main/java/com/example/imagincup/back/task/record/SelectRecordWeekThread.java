package com.example.imagincup.back.task.record;

import android.util.Log;

import com.example.imagincup.Constants;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Calendar;

public class SelectRecordWeekThread extends Thread {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private String PersonId;

    private String startDate;
    private String lastweekdate;

    private Integer weekdate1;
    private Integer weekdate2;
    private Integer weekdate3;
    private Integer weekdate4;
    private Integer weekdate5;
    private Integer weekdate6;
    private Integer weekdate7;

    private Date date;

    private Integer resultScore;

    public SelectRecordWeekThread(String personID, String startDate, String lastweekdate) {
        this.PersonId = personID;
        this.startDate = startDate;
        this.lastweekdate = lastweekdate;

        this.weekdate1 = 0;
        this.weekdate2 = 0;
        this.weekdate3 = 0;
        this.weekdate4 = 0;
        this.weekdate5 = 0;
        this.weekdate6 = 0;
        this.weekdate7 = 0;
    }

    @Override
    public void run() {

        Calendar cal = Calendar.getInstance();
        try {
            connection = DriverManager.getConnection(Constants.DATABASE_CONNECTION_URL);
            statement = connection.createStatement();

            String query = "SELECT * FROM Record WHERE PersonID='"
                    + PersonId
                    + "' AND Date between '" + lastweekdate + "' AND '"
                    + startDate + "';";
            resultSet = statement.executeQuery(query);
            Log.d("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^", String.valueOf(query));

            while (resultSet.next()){
                resultScore = resultSet.getInt("DepressionScore");
                date = resultSet.getDate("Date");
                cal.setTime(date);
                weekDate(cal.get(Calendar.DAY_OF_WEEK), resultScore);
                Log.d("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^", String.valueOf(resultScore));
                Log.d("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^", resultSet.getString("Date"));
                Log.d("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^", String.valueOf(date));
                //Log.d("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^", String.valueOf(cal));
                Log.d("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^", String.valueOf(cal.get(Calendar.DAY_OF_WEEK)));

            }
        }
        catch(Exception exception){
            Log.d("error", exception.getMessage());
        }
        finally {
            // 시간 나면 코드 클래스로
            if (resultSet != null) try { resultSet.close(); } catch(SQLException ex) {}
            if (statement != null) try { statement.close(); } catch(SQLException ex) {}
            if (connection != null) try { connection.close(); } catch(SQLException ex) {}
        }
    }
    private void weekDate(Integer dayOfWeek, Integer score){
        switch (dayOfWeek) {
            case 1:
                this.weekdate1 = score;
                //"일";
                break;
            case 2:
                this.weekdate2 = score;
                // "월";
                break;
            case 3:
                this.weekdate3 = score;
                // "화";
                break;
            case 4:
                this.weekdate4 = score;
                // "수";
                break;
            case 5:
                this.weekdate5 = score;
                // "목";
                break;
            case 6:
                this.weekdate6 = score;
                // "금";
                break;
            case 7:
                this.weekdate6 = score;
                // "토";
                break;
        }
    }

    public Integer getScoreweek1(){ return this.weekdate1; }
    public Integer getScoreweek2(){ return this.weekdate2; }
    public Integer getScoreweek3(){ return this.weekdate3; }
    public Integer getScoreweek4(){ return this.weekdate4; }
    public Integer getScoreweek5(){ return this.weekdate5; }
    public Integer getScoreweek6(){ return this.weekdate6; }
    public Integer getScoreweek7(){ return this.weekdate7; }
}
