package com.pucpr.br.bsi2015.tcc.selfiecode.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pucpr.br.bsi2015.tcc.selfiecode.dao.CodigoFonteDAO;
import com.pucpr.br.bsi2015.tcc.selfiecode.dao.DesenvolvedorDAO;
import com.pucpr.br.bsi2015.tcc.selfiecode.dao.DicaDAO;
import com.pucpr.br.bsi2015.tcc.selfiecode.dao.MetricaDAO;
import com.pucpr.br.bsi2015.tcc.selfiecode.dao.ProjetoDAO;
import com.pucpr.br.bsi2015.tcc.selfiecode.dao.UsuarioDAO;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.CodigoFonte;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Desenvolvedor;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Dica;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Metrica;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Projeto;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.TipoUsuario;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Usuario;

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
		CodigoFonte cf = new CodigoFonte();
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
		        
		        cf.setMetricas(metricas);
		       
		        md.preencherIntervalos(m);
		       	
		        dd.buscarDicas(m);
		        
		        metricas.add(m);
		       
		    } catch (JSONException e) {
		        // Something went wrong!
		    }
		}
		cf.setMetricas(metricas);
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
		
		Desenvolvedor ds = new Desenvolvedor();
		Projeto proj = new Projeto();
		TipoUsuario tu = new TipoUsuario();
		List<Projeto> projs = new ArrayList<Projeto>();
		boolean result;
		ds.setNome(u.getString("nome"));
		ds.setLogin(u.getString("login"));
		ds.setCpf(u.getLong("cpf"));
		ds.setDataNascimento(new Date(u.getString("dataNascimento")));
		ds.setSenha(u.getString("senha"));
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
		
			
		if(dDao.selectDev(ds) != null)
		{
			result = false;
		}
		else if(dDao.selectDevByLogin(ds) != null)
		{
			result = false;
		}
		else if(dDao.selectDevInativo(ds) != null)
		{
			result = false;
		}
		else
		{
			result = dDao.cadastrarDev(ds);
			uDao.insertTipo(ds);
			pDao.inserDevProj(ds);
		}
		
		return result;
	}
	
	public JSONObject editDev(JSONObject u, Usuario g)
	{
		
		Desenvolvedor ds = new Desenvolvedor();
		JSONObject r = new JSONObject();
		ds.setNome(u.getString("nome"));
		ds.setLogin(u.getString("login"));
		ds.setCpf(u.getLong("cpfNovo"));
		ds.setDataNascimento(new Date(u.getString("dataNascimento")));


		DesenvolvedorDAO dDao = new DesenvolvedorDAO();	
		Usuario us = dDao.selectDev(ds);
		Usuario usl = dDao.selectDevByLogin(ds);

		if(us != null && us.getCpf() != u.getLong("cpf"))
		{
			r.put("result", "existe");
		}
		else if(usl != null && usl.getCpf() != u.getLong("cpf"))
		{
			r.put("result", "existe");
		}
//		else if( (us = dDao.selectDevInativo(ds))!= null)
//		{
//			r.put("result", "inativo");
//			r.put("usuario", us);
//		}
		else
		{
			dDao.editarDev(ds, u.getLong("cpf"));
			r.put("result", true);
		}
		return r ;
	}
	
	public boolean cadastrarProj(JSONObject p, Usuario g)
	{
		

		Projeto proj = new Projeto();
	
		
		proj.setNome(p.getString("nome"));
		proj.setInicio(new Date(p.getString("inicio")));
		proj.setFim(new Date(p.getString("fim")));
		
		proj.setStatus(p.getString("status"));
		proj.setDescricao(p.getString("descricao"));
		TipoUsuario tu = new TipoUsuario();


		ProjetoDAO pDao = new ProjetoDAO();			
		
		boolean result = pDao.inserirProjeto(proj);
		pDao.inserirGerProj(g, proj);
		
		return result;
	}
	
	public boolean editarProj(JSONObject p, Usuario g)
	{
		

		Projeto proj = new Projeto();
		proj.setId(p.getInt("id"));
		proj.setNome(p.getString("nome"));
		SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");
	    // myDate is the java.util.Date in yyyy-mm-dd format
	    // Converting it into String using formatter
		Date inicio = null;
		Date fim = null;
		try {
			inicio = sm.parse(p.getString("inicio"));
			fim = sm.parse(p.getString("fim"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		proj.setInicio(inicio);
		proj.setFim(fim);
		proj.setStatus(p.getString("status"));
		proj.setDescricao(p.getString("descricao"));

		ProjetoDAO pDao = new ProjetoDAO();			
		
		boolean result = pDao.alterarProjeto(proj);

		
		return result;
	}

	public boolean excluirDev(JSONObject u, Usuario g) {
		Desenvolvedor ds = new Desenvolvedor();
		
		ds.setNome(u.getString("nome"));
		ds.setLogin(u.getString("login"));
		ds.setCpf(u.getLong("cpf"));



		DesenvolvedorDAO dDao = new DesenvolvedorDAO();
		return dDao.excluirDev(ds);
	}
	
	public boolean excluirProj(JSONObject p, Usuario g) {
		Projeto proj = new Projeto();
		ProjetoDAO pDao = new ProjetoDAO();
		
		proj.setId(p.getInt("id"));


		return pDao.excluirProj(proj);
	}
	
	public boolean atribuirDev(JSONObject a,  String key)
	{
		
		Desenvolvedor ds = new Desenvolvedor();
		Projeto proj = new Projeto();
		List<Projeto> projetos = new ArrayList<Projeto>();
		
		boolean result = true;
		
		proj.setId(a.getInt("proj"));
		projetos.add(proj);
		ds.setCpf(a.getLong("dev"));
		ds.setProjetos(projetos);
		
		ProjetoDAO pDao = new ProjetoDAO();
		
		int rP = pDao.checkDevProj(ds);
		
		if(rP > 0)
		{
			rP = pDao.inserDevProj(ds);
		
			if(rP > 0)
				result = false;
		}
		else
			result = false;
		
		return result;
	}

	public JSONArray exibirMetricas(JSONObject usuario, JSONObject projeto, Usuario u) {
		// TODO Auto-generated method stub
		Desenvolvedor ds = new Desenvolvedor();
		Projeto proj = new Projeto();
		List<Projeto> projetos = new ArrayList<Projeto>();
		List<CodigoFonte> cfs;
		boolean result = true;
		CodigoFonte cf = new CodigoFonte();
		List<Metrica> metricas = new ArrayList<Metrica>();
		Metrica metrica;
		
		proj.setId(projeto.getInt("proj"));
		
		//seleciona Dev
		DesenvolvedorDAO dDao = new DesenvolvedorDAO();
		ds.setCpf(usuario.getLong("dev"));
		
		ds = dDao.selectDev(ds);
		

		//seleciona Projeto
		ProjetoDAO pDao = new ProjetoDAO();
		
		proj = pDao.selectProjeto(proj);
		
		projetos.add(proj);
		ds.setProjetos(projetos);
		
		CodigoFonteDAO cDao = new CodigoFonteDAO();
		cfs = cDao.selectCodigosFonte( ds , proj);
		proj.setCodigoFonte(cfs);
		
		MetricaDAO mDao = new MetricaDAO();
		
		for (int i = 0; i < cfs.size(); i++) {
			cf = cfs.get(i);
			metricas = mDao.selectMetricas(cfs.get(i));
			cf.setMetricas(metricas);
			metricas = null;
		}
		
		return new JSONArray(cfs);
	}


}
