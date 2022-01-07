package com.example.imagincup;

public final class Constants {
    public static final String	JDBC_DRIVER	= "net.sourceforge.jtds.jdbc.Driver";
    public static final String	DATABASE_CONNECTION_URL	= "jdbc:jtds:sqlserver://imagine-cup-server.database.windows.net:1433;DatabaseName=epqp;user=na0@imagine-cup-server;password=a12345678*;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;ssl=request";

    public static final String QUERY_SELECT_PERSON_DATABASE = "select * from Person";

    public static final String DATABASE_EXIST = "exist";
    //public static final String DATABASE_PERSONID = "PersonId";

}
