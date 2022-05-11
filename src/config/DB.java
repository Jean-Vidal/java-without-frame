package config;

import java.sql.*;

public class DB {
    private static Connection con = null;

    public static Connection conectaDb(){
        String url = "jdbc:mysql://localhost:3306/coursejdbc?user=root&password=1234";
        try{
            con = DriverManager.getConnection(url);
            System.out.println("Conectado!");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return con;
    }
}
