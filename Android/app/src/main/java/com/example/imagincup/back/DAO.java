package com.example.imagincup.back;

import com.example.imagincup.activity.mission.Record;
import com.example.imagincup.back.DTO.DTOPerson;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DAO{

    public String CheckPersonExist();
    public String PersonTableAsyncTask();


}
