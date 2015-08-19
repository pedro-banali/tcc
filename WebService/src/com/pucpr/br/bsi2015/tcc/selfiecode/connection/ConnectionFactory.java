package com.pucpr.br.bsi2015.tcc.selfiecode.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
//	private static final String DB_CONNECTION = "jdbc:mysql://192.168.112.129:3306/selfieCode";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/selfieCode";
	private static final String DB_USER = "root";
//	private static final String DB_PASSWORD = "tcc@051526";
	private static final String DB_PASSWORD = "tcc@6274262";
	
	public static Connection getConnection()
	{
		Connection dbConnection = null;
		 
		try {
 
			Class.forName(DB_DRIVER);
 
		} catch (ClassNotFoundException e) {
 
			System.out.println(e.getMessage());
 
		}
 
		try {
 
			dbConnection = DriverManager.getConnection(
                             DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;
 
		} catch (SQLException e) {
 
			System.out.println(e.getMessage());
 
		}
 
		return dbConnection;
	}	
}
