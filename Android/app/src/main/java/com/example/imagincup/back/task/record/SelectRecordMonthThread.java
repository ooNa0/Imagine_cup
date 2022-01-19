package com.example.imagincup.back.task;

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

public class SelectRecordMonthThread extends Thread {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private String PersonId;

    private String selectYear;
    private String selectMonth;
    private String selectDay;

    private String startDate;
    private String endDate;

    private Date date;
    private Integer monthInteger;

    private Float resultAverage;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMMM-dd", Locale.US);
    private SimpleDateFormat dateFormatInteger = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dateFormatNotDay = new SimpleDateFormat("yyyyMM");

    public SelectRecordMonthThread(String personID, String startDate, String endDate) {
        this.PersonId = personID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public void run() {
        try {
            connection = DriverManager.getConnection(Constants.DATABASE_CONNECTION_URL);
            statement = connection.createStatement();
            String query = "SELECT AVG(DepressionScore) FROM Record WHERE PersonID='"
                    + PersonId
                    + "' AND Date between '" + startDate + "' AND '"
                    + endDate + "';";
            resultSet = statement.executeQuery(query);
            //SELECT SUM(AMOUNT) FROM TIME WHERE REGDATE > "2018-05-02" AND REGDATE < "2018-06-15";
            Log.d("***********************************----------- ", query);

            while (resultSet.next()){
                resultAverage = resultSet.getFloat(1);
                Log.d("____________________________________", String.valueOf(resultAverage));

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

    public Float getResultAverage(){
        return resultAverage;
    }
}

