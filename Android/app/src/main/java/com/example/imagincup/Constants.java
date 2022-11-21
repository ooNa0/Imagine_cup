package com.example.imagincup;

public final class Constants {
    public static final String	JDBC_DRIVER	= "net.sourceforge.jtds.jdbc.Driver";
    public static final String	DATABASE_CONNECTION_URL	=
            "jdbc:jtds:sqlserver://academic-server.database.windows.net:1433;DatabaseName=academic-database;user=a@academic-server;password=b12345678*;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;ssl=require";
    public static final String QUERY_SELECT_PERSON_DATABASE = "select * from Person";

    public static final Integer RUNNING = 0;
    public static final Integer DATABASE_EXIST = 1;
    public static final Integer DATABASE_NOT_EXIST = 2;
    public static final String DATABASE_PERSON_TABLENAME = "Person";
    public static final String DATABASE_RECORD_TABLENAME = "Record";

    public static final Integer DAY_RECORD_FRAGMENT = 0;
    public static final Integer MONTH_RECORD_FRAGMENT = 1;

    public static final String MISSION_DEFAULT = "0";

}
