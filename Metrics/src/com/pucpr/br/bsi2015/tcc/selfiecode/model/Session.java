package com.pucpr.br.bsi2015.tcc.selfiecode.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Session {
	private String userName;
	private int userId;
	private String sessionId;
	private Map<Integer, String> projetos = new HashMap<Integer, String>();
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Map<Integer, String> getProjetos() {
		return projetos;
	}
	public void setProjetos(Map<Integer, String> projetos) {
		this.projetos = projetos;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	
	
}
