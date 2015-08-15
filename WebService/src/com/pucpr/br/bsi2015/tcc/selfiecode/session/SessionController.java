package com.pucpr.br.bsi2015.tcc.selfiecode.session;

import java.util.HashMap;
import java.util.Map;

import com.pucpr.br.bsi2015.tcc.selfiecode.model.Usuario;

public class SessionController {
	private static SessionController sc = new SessionController();
	Map<String, Usuario> listSessions = new HashMap<String, Usuario>();
	private SessionController()
	{
		
	}
	
	public static SessionController getInstance()
	{
		return sc;
	}
	
	public void addSession(String key, Usuario u)
	{
		listSessions.put(key, u);
	}
	
	public Usuario getUser(String key)
	{
		return listSessions.get(key);
	}
	
	
	public boolean deleteSession(String key)
	{
		boolean r;
		if(listSessions.containsKey(key))
		{
			r= true;
			listSessions.remove(key);
		}
		else
			r =false;
		
		
		return r;
	}
}
