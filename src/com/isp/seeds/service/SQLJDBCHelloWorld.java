package com.isp.seeds.service;

//STEP 1. Import required packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLJDBCHelloWorld {
 // JDBC driver name and database URL
 static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
 static final String DB_URL = "jdbc:mysql://127.0.0.1/seeds?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

 //  Database credentials
 static final String USER = "eclipse";
 static final String PASS = "eclipse";
 
 public static void main(String[] args) {
 Connection conn = null;
 Statement stmt = null;
 try{
    //STEP 2: Register JDBC driver
    Class.forName(JDBC_DRIVER); /* Crea unha instancia de driver*/

    //STEP 3: Open a connection
    System.out.println("Connecting to database...");
    conn = DriverManager.getConnection(DB_URL,USER,PASS);

    //STEP 4: Execute a query
    System.out.println("Creating statement...");
    stmt = conn.createStatement();
    String sql;
    sql = "SELECT ID_USUARIO, NICK, EMAIL FROM Usuario";
    ResultSet seeds = stmt.executeQuery(sql);

    //STEP 5: Extract data from result set
    while(seeds.next()){
       //Retrieve by column name
    	
       int id  = seeds.getInt("ID_USUARIO");
       String nick = seeds.getString("nick");
       String email = seeds.getString("email");

       //Display values
       System.out.print("EmployeeId: " + id);
       System.out.print(",  FirstName: " + nick);
       System.out.println(", LastName: " + email);
    }
    //STEP 6: Clean-up environment
    seeds.close();
    stmt.close();
    conn.close();
 }catch(SQLException se){
    //Handle errors for JDBC
    se.printStackTrace();
 }catch(Exception e){
    //Handle errors for Class.forName
    e.printStackTrace();
 }finally{
    //finally block used to close resources
    try{
       if(stmt!=null)
          stmt.close();
    }catch(SQLException se2){
    }// nothing we can do
    try{
       if(conn!=null)
          conn.close();
    }catch(SQLException se){
       se.printStackTrace();
    }//end finally try
 }//end try
 System.out.println("Goodbye!");
}//end main
}//end FirstExample