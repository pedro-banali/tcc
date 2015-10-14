package com.pucpr.br.bsi2015.tcc.selfiecode.controller;

import com.pucpr.br.bsi2015.tcc.selfiecode.model.Session;

public class SessionController {
	
	private static SessionController instance = new SessionController();
	private Session session;

	private SessionController ()
	{
		session = new Session();
		
		
	}
	
	
	
	
	public static SessionController getInstance()
	{
		return  instance;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	
}
