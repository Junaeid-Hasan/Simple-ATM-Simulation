package JDBC;

import java.sql.*;

public class ConnectDB{
    Connection c;
    public Statement s;
    public ConnectDB(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            c =DriverManager.getConnection("jdbc:mysql:///bank_management_system","root","root");
            s =c.createStatement();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
