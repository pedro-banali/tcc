package com.pucpr.br.bsi2015.tcc.selfiecode.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.pucpr.br.bsi2015.tcc.selfiecode.dao.DesenvolvedorDAO;
import com.pucpr.br.bsi2015.tcc.selfiecode.dao.DicaDAO;
import com.pucpr.br.bsi2015.tcc.selfiecode.dao.MetricaDAO;
import com.pucpr.br.bsi2015.tcc.selfiecode.dao.ProjetoDAO;
import com.pucpr.br.bsi2015.tcc.selfiecode.dao.UsuarioDAO;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Desenvolvedor;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Dica;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Metrica;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Projeto;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.TipoUsuario;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Usuario;
import com.pucpr.br.bsi2015.tcc.selfiecode.session.SessionController;

public class SelfieCodeBC {
	
	private static SelfieCodeBC selfieCodeBC = new SelfieCodeBC();
	
	private SelfieCodeBC()
	{
		
	}
	
	public static SelfieCodeBC getInstance()
	{
		return selfieCodeBC;	
	}
	
	public Usuario login(String usuario, String senha)
	{
		
		Usuario u = new Usuario();
		u.setLogin(usuario);
		u.setSenha(senha);
		
		UsuarioDAO uDao = new UsuarioDAO();
		
		return uDao.login(u);
		
		//return true;
	}
	
	public List<Metrica> dicas(JSONObject jsonObject)
	{
		List<Metrica> metricas = new ArrayList<Metrica>();
		List<Dica> dicas = new ArrayList<Dica>();
		Metrica m;
		
		MetricaDAO md = new MetricaDAO();
		DicaDAO dd = new DicaDAO();
		Iterator<String> iter = jsonObject.keys();
		while (iter.hasNext()) {
		    String key = iter.next();
		    try {
		        Float value = new Float((String) jsonObject.get(key));
		        m = new Metrica();
		        m.setSigla(key);
		        m.setValorMetrica(value);
		       
		        md.preencherIntervalos(m);
		       	
		        dd.buscarDicas(m);
		        
		        metricas.add(m);
		       
		    } catch (JSONException e) {
		        // Something went wrong!
		    }
		}
		
		return metricas;
	}
	
	public List<Usuario> listarDesenvolvedores(Usuario u)
	{
		
		
		
		UsuarioDAO uDao = new UsuarioDAO();
		
		return uDao.listarDev(u);
		
		//return true;
	}
	
	public List<Projeto> listarProjetos(Usuario u)
	{
			
		ProjetoDAO pDao = new ProjetoDAO();
		
		return pDao.selectProjetos(u);
		
		//return true;
	}

	public boolean cadastrarDev(JSONObject u, Usuario g)
	{
		
		SessionController sc = SessionController.getInstance();
		Desenvolvedor ds = new Desenvolvedor();
		Projeto proj = new Projeto();
		TipoUsuario tu = new TipoUsuario();
		List<Projeto> projs = new ArrayList<Projeto>();
		
		ds.setNome(u.getString("nome"));
		ds.setLogin(u.getString("login"));
		ds.setCpf(u.getLong("cpf"));
		ds.setDataNascimento(new Date(u.getString("data")));
		ds.setSenha(u.getString("nome"));
		tu.setId(3);
		ds.setTipoUsuario(tu);
		ds.setDataCadastro(new Date());
		ds.setGerente(g);
		proj.setId(u.getInt("projeto"));
		projs.add(proj);
		ds.setProjetos(projs);

		DesenvolvedorDAO dDao = new DesenvolvedorDAO();
		ProjetoDAO pDao = new ProjetoDAO();
		UsuarioDAO uDao = new UsuarioDAO();
		
			
		
		boolean result = dDao.cadastrarDev(ds);
		uDao.insertTipo(ds);
		pDao.inserDevProj(ds);
		
		return result;
	}
}
