package com.pucpr.br.bsi2015.tcc.selfiecode.connection;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SessionFactory {

	private static Session session;
	private int lport;
	private String rhost;
	private int rport;
  
	private SessionFactory() {
		JSch jsch = new JSch();
		try {
			jsch.addIdentity("C:\\Users\\pedro-banali\\Downloads\\AWSKey.pem");

			session = jsch.getSession("ubuntu", "ec2-52-88-229-56.us-west-2.compute.amazonaws.com", 22);
			lport = 4321;
			rhost = "localhost";
			rport = 3306;
			// session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");

			session.connect();
			int assinged_port = session.setPortForwardingL(lport, rhost, rport);
			System.out.println("localhost:" + assinged_port + " -> " + rhost + ":" + rport);
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// public Session getSession()
	// {
	//
	// }
}
