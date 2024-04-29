package com.example.telas_iniciais;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class connection {
    protected static String db = "fecapsocialdb";

    protected static String ip = "fecapsocialbd.mysql.database.azure.com";

    protected static String port = "3306";

    protected static String user = "fecapsocial";

    protected static String pass = "Hamburgada@";

    public Connection CONN(){
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String connectionString = "jdbc:mysql://" + ip + ":" + port + "/" + db;
            conn = DriverManager.getConnection(connectionString, user, pass);

        } catch (Exception e){
            Log.e("ERRO", Objects.requireNonNull(e.getMessage()));

        }
        return conn;
    }
}

