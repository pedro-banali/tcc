package com.pucpr.br.bsi2015.tcc.selfiecode.service;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pucpr.br.bsi2015.tcc.selfiecode.controller.SelfieCodeBC;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Metrica;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Projeto;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Usuario;
import com.pucpr.br.bsi2015.tcc.selfiecode.session.SessionController;

@Path("service")
public class WebService {
	@GET
	@Produces("application/json")
	public Response convertFtoC() throws JSONException {

		JSONObject jsonObject = new JSONObject();
		Double fahrenheit = 98.24;
		Double celsius;
		celsius = (fahrenheit - 32) * 5 / 9;
		jsonObject.put("F Value", fahrenheit);
		jsonObject.put("C Value", celsius);

		String result = "" + jsonObject;
		return Response.status(200).entity(result).build();
	}

	@Path("{f}")
	@GET
	@Produces("application/json")
	public Response convertFtoCfromInput(@PathParam("f") float f) throws JSONException {

		JSONObject jsonObject = new JSONObject();
		float celsius;
		celsius = (f - 32) * 5 / 9;
		jsonObject.put("F Value", f);
		jsonObject.put("C Value", celsius);

		String result = "" + jsonObject;
		return Response.status(200).entity(result).build();
	}

	@Path("login")
	@POST
	@Produces("application/json")
	public Response login(@QueryParam("user") String user, @QueryParam("pass") String pass) throws JSONException {
		SessionController sc = SessionController.getInstance();
		JSONObject jsonObject = new JSONObject();
		Usuario loginStatus;
		String loginResult;
		SelfieCodeBC sbc = SelfieCodeBC.getInstance();

		loginStatus = sbc.login(user, pass);

		if (loginStatus != null) {
			
			MessageDigest md;
			Date date = new Date();
			String tempo = "" + date.getTime();
			

			try {
				md = MessageDigest.getInstance("MD5");
				md.update(tempo.getBytes(Charset.forName("UTF-8")));
				loginResult = String.format(Locale.ROOT, "%032x", new BigInteger(1, md.digest()));
				jsonObject.put("result", loginResult);
				jsonObject.put("username", loginStatus.getNome());
				jsonObject.put("tipo", loginStatus.getTipoUsuario().getId());
				
				sc.addSession(loginResult, loginStatus);
				

			} catch (NoSuchAlgorithmException e) {
				throw new IllegalStateException(e);
			}
		}
		else
		{
			jsonObject.put("result", "login inexistente");
		}

		String result = "" + jsonObject;
		return Response.status(200).entity(result).build();
	}
	
	@Path("logout")
	@POST
	@Produces("application/json")
	public Response logout(@HeaderParam("key") String key) throws JSONException {
		SessionController sc = SessionController.getInstance();
		
		boolean r = sc.deleteSession(key);
		
		String result = "" + r;
		return Response.status(200).entity(result).build();
	}
	
	@Path("dicas")
	@POST
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response dicas(@FormParam("json") String json, @FormParam("session") String session ) throws JSONException {
		
		JSONObject jsonObject = new JSONObject(json);
		SelfieCodeBC sbc = SelfieCodeBC.getInstance();

		JSONObject jsonValues = jsonObject.getJSONObject("values");
		JSONObject jsonResponse = new JSONObject();
		List<Metrica> metricas = sbc.dicas(jsonValues);
		
		for (int i = 0; i < metricas.size(); i++) {
			for (int j = 0; j < metricas.get(i).getDicas().size(); j++) {
				jsonResponse.put(metricas.get(i).getSigla(),  metricas.get(i).getDicas().get(j).getDescricao());
			}
		}
		
		
		String result = "" + jsonResponse;
		return Response.status(200).entity(result).build();
		//return ;
	}
	
	
	@Path("cadastroDev")
	@POST
	//@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastroDev(@HeaderParam("usuario") String usuario, @HeaderParam("key") String key ) throws JSONException {
		SessionController sc = SessionController.getInstance();
		boolean result;
		JSONObject jsonObject = new JSONObject(usuario);

		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		
		Usuario u = sc.getUser(key);
		result = sbc.cadastrarDev(jsonObject, u );

		
		jsonObject = new JSONObject();
		jsonObject.put("result", result);
		

		return Response.status(200).entity(jsonObject.toString()).build();
		//return ;
	}
	
	@Path("atribuir")
	@POST
	//@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response atribuir(@HeaderParam("atribuicao") String atribuicao, @HeaderParam("key") String key ) throws JSONException {
		SessionController sc = SessionController.getInstance();
		boolean result;
		JSONObject a = new JSONObject(atribuicao);

		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		
		Usuario u = sc.getUser(key);
		result = sbc.atribuirDev(a, key);

		
		a = new JSONObject();

		a.put("result", result);
		

		return Response.status(200).entity(a.toString()).build();
		//return ;
	}
	
	@Path("editDev")
	@POST
	//@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editDev(@HeaderParam("usuario") String usuario, @HeaderParam("key") String key ) throws JSONException {
		SessionController sc = SessionController.getInstance();
		boolean result;
		JSONObject jsonObject = new JSONObject(usuario);

		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		
		Usuario u = sc.getUser(key);
		result = sbc.editDev(jsonObject, u );

		
		jsonObject = new JSONObject();
		jsonObject.put("result", result);
		

		return Response.status(200).entity(jsonObject.toString()).build();
		//return ;
	}
	
	@Path("listarDev")
	@POST
	@Produces("application/json")
	public Response listarDev(@HeaderParam("key") String key) throws JSONException {
		SessionController sc = SessionController.getInstance();
		JSONArray list;
		JSONObject jSon = new JSONObject();
		Usuario usuario = sc.getUser(key);
		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		List<Usuario> uss = sbc.listarDesenvolvedores(usuario);
		
		list = new JSONArray(uss);
		jSon.put("devs", list);
		return Response.status(200).entity(jSon.toString()).build();
	}
	
	@Path("excluirDev")
	@POST
	@Produces("application/json")
	public Response excluirDev(@HeaderParam("usuario") String usuario, @HeaderParam("key") String key) throws JSONException {
		SessionController sc = SessionController.getInstance();
		
		JSONObject jSon = new JSONObject(usuario);
		Usuario u = sc.getUser(key);
		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		boolean uss = sbc.excluirDev(jSon, u);
		
;
		jSon.put("result", uss);
		return Response.status(200).entity(jSon.toString()).build();
	}
	
	@Path("excluirProj")
	@POST
	@Produces("application/json")
	public Response excluirProj(@HeaderParam("projeto") String projeto, @HeaderParam("key") String key) throws JSONException {
		SessionController sc = SessionController.getInstance();
		
		JSONObject jSon = new JSONObject(projeto);
		Usuario u = sc.getUser(key);
		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		boolean uss = sbc.excluirProj(jSon, u);
		
;
		jSon.put("result", uss);
		return Response.status(200).entity(jSon.toString()).build();
	}
	
	
	@Path("listarProj")
	@POST
	@Produces("application/json")
	public Response listarProj(@HeaderParam("key") String key) throws JSONException {
		SessionController sc = SessionController.getInstance();
		JSONArray list;
		JSONObject jSon = new JSONObject();
		Usuario usuario = sc.getUser(key);
		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		List<Projeto> upp = sbc.listarProjetos(usuario);
		
		list = new JSONArray(upp);
		jSon.put("proj", list);
		return Response.status(200).entity(jSon.toString()).build();
	}
	
	@Path("cadastroProj")
	@POST
	//@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastroProj(@HeaderParam("projeto") String projeto, @HeaderParam("key") String key ) throws JSONException {
		SessionController sc = SessionController.getInstance();
		boolean result;
		JSONObject jsonObject = new JSONObject(projeto);

		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		
		Usuario u = sc.getUser(key);
		result = sbc.cadastrarProj(jsonObject, u );

		
		jsonObject = new JSONObject();
		jsonObject.put("result", result);
		

		return Response.status(200).entity(jsonObject.toString()).build();
		//return ;
	}
	
	@Path("editProj")
	@POST
	//@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editProj(@HeaderParam("projeto") String projeto, @HeaderParam("key") String key ) throws JSONException {
		SessionController sc = SessionController.getInstance();
		boolean result;
		JSONObject jsonObject = new JSONObject(projeto);

		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		
		Usuario u = sc.getUser(key);
		result = sbc.editarProj(jsonObject, u );

		
		jsonObject = new JSONObject();
		jsonObject.put("result", result);
		

		return Response.status(200).entity(jsonObject.toString()).build();
		//return ;
	}
}
