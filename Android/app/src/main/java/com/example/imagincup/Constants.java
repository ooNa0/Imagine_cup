package com.example.imagincup;

public final class Constants {
    public static final String	JDBC_DRIVER	= "net.sourceforge.jtds.jdbc.Driver";
    //public static final String	DATABASE_CONNECTION_URL	= "jdbc:jtds:sqlserver://59.25.103.41:1433;DatabaseName=epqp;user=na0@imagine-cup-server;password=a12345678*;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;ssl=require";
    public static final String	DATABASE_CONNECTION_URL	= "jdbc:jtds:sqlserver://imagine-cup-server.database.windows.net:1433;DatabaseName=epqp;user=na0@imagine-cup-server;password=a12345678*;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;ssl=require";
    //public static final String	DATABASE_CONNECTION_URL	= "jdbc:sqlserver://imagine-cup-server.database.windows.net:1433;database=epqp;user=na0@imagine-cup-server;password=a12345678*;encrypt=false;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;ssl=require";
    public static final String QUERY_SELECT_PERSON_DATABASE = "select * from Person";

    public static final Integer RUNNING = 0;
    public static final Integer DATABASE_EXIST = 1;
    public static final Integer DATABASE_NOT_EXIST = 2;
    public static final String DATABASE_PERSON_TABLENAME = "Person";

    public static final Integer DAY_RECORD_FRAGMENT = 0;
    public static final Integer MONTH_RECORD_FRAGMENT = 1;


    public static final Integer MISSION_COMPLETE = 0;
    public static final Integer MISSION_FAIL = 1;
    public static final Integer MISSION_DEFAULT = 2;

}
