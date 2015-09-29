package com.pucpr.br.bsi2015.tcc.selfiecode.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class ConnectionFactory {
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	// private static final String DB_CONNECTION =
	// "jdbc:mysql://192.168.112.129:3306/selfieCode";
	// private static final String DB_CONNECTION =
	// "jdbc:mysql://localhost:3306/selfieCode";
	private static final String DB_CONNECTION = "jdbc:mysql://selfiecodetcc.cono6s1iznze.us-west-2.rds.amazonaws.com:3306/selfiecode";
//	private static final String DB_USER = "root";
	private static final String DB_USER = "tcc";
	// private static final String DB_PASSWORD = "tcc@051526";
	// private static final String DB_PASSWORD = "tcc@6274262";
	private static final String DB_PASSWORD = "tcc051526";

	private static Session session;

	public static Connection getConnection() {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);

		} catch (Exception e) {
			System.err.print(e);
		}
		// dbConnection = DriverManager.getConnection(
		// DB_CONNECTION, DB_USER,DB_PASSWORD);

		return dbConnection;
	}

}
