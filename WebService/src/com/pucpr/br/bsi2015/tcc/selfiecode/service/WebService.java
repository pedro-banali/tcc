package com.pucpr.br.bsi2015.tcc.selfiecode.service;

import java.io.File;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.Consumes;
import javax.ws.rs.Encoded;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Treino;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Usuario;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.UsuarioProj;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.UsuarioTreino;
import com.pucpr.br.bsi2015.tcc.selfiecode.session.SessionController;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import sun.misc.IOUtils;


@Path("service")
public class WebService {
	
	private static final String SERVER_UPLOAD_LOCATION_FOLDER = "C://selfieCode/";
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


	@Path("login")
	@POST
	@Produces("application/json")
	public Response login(@QueryParam("user") String user, @QueryParam("pass") String pass) throws JSONException {
		SessionController sc = SessionController.getInstance();
		JSONObject jsonObject = new JSONObject();
		Usuario loginStatus;
		String loginResult;
		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		JSONArray ja = new JSONArray();
		//List<String> lista =  new ArrayList<String>();
		loginStatus = sbc.login(user, pass);
		JSONObject jp = new JSONObject();
		if (loginStatus != null && loginStatus.getCpf() != 0 ) {
			
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
				List<Projeto> projs = sbc.listarProjetos(loginStatus);
				//ja = new JSONArray(projs);
				
				ja = new JSONArray();
				for (int i = 0; i < projs.size(); i++) {
					jp = new JSONObject();
					jp.put("nome", projs.get(i).getNome());
					jp.put("codigoProj", projs.get(i).getId());
					jp.put("tempoColeta", projs.get(i).getTempoParaColeta());
					ja.put(jp);

				}
				
				
				jsonObject.put("projetos", ja);
				
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
	@Produces(MediaType.APPLICATION_JSON )
	public Response dicas(@FormParam("json") String json, @FormParam("session") String session ) throws JSONException {
		
		JSONObject jsonObject = new JSONObject(json);
		SelfieCodeBC sbc = SelfieCodeBC.getInstance();

		JSONObject jsonValues = jsonObject.getJSONObject("values");
		JSONObject jsonResponse = new JSONObject();
		List<Metrica> metricas = sbc.dicas(jsonValues);
		String dateS = jsonObject.getString("date");
		String fileName = jsonObject.getString("fileName");
		String classe = jsonObject.getString("handle");
		String projeto = jsonObject.getString("projeto");
		int projId = jsonObject.getInt("projId");
		String sessionId = jsonObject.getString("sessionId");
		sbc.salvarCodigoFonte(metricas, dateS, fileName, classe, projId, sessionId);
		
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
	
	
	@Path("cadastroGer")
	@POST
	//@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastroGer(@HeaderParam("usuario") String usuario, @HeaderParam("key") String key ) throws JSONException {
		SessionController sc = SessionController.getInstance();
		boolean result;
		JSONObject jsonObject = new JSONObject(usuario);

		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		
		Usuario u = sc.getUser(key);
		
		result = sbc.cadastrarGer(jsonObject, u );
		
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
	
	@Path("verifySession")
	@POST
	//@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response verifySession(@HeaderParam("key") String key ) throws JSONException {
		SessionController sc = SessionController.getInstance();

		JSONObject a = new JSONObject();

		
		Usuario u = sc.getUser(key);
		if(u == null)
		{
			a = new JSONObject();
			a.put("result", "expired");
		}
		else
			a.put("result", "valid");

		

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
		JSONObject jsonResponse;
		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		
		Usuario u = sc.getUser(key);
		
		jsonResponse = sbc.editDev(jsonObject, u );

		return Response.status(200).entity(jsonResponse.toString()).build();
		//return ;
	}
	
	@Path("editGer")
	@POST
	//@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editGer(@HeaderParam("usuario") String usuario, @HeaderParam("key") String key ) throws JSONException {
		SessionController sc = SessionController.getInstance();
		boolean result;
		JSONObject jsonObject = new JSONObject(usuario);
		JSONObject jsonResponse;
		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		
		Usuario u = sc.getUser(key);
		
		jsonResponse = sbc.editGer(jsonObject, u );

		return Response.status(200).entity(jsonResponse.toString()).build();
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
	
	@Path("listarGer")
	@POST
	@Produces("application/json")
	public Response listarGer(@HeaderParam("key") String key) throws JSONException {
		SessionController sc = SessionController.getInstance();
		JSONArray list;
		JSONObject jSon = new JSONObject();
		Usuario usuario = sc.getUser(key);
		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		List<Usuario> uss = sbc.listarGerentes(usuario);
		
		list = new JSONArray(uss);
		jSon.put("ger", list);
		return Response.status(200).entity(jSon.toString()).build();
	}
	
	@Path("listarDevProj")
	@POST
	@Produces("application/json")
	public Response listarDevProj(@HeaderParam("key") String key) throws JSONException {
		SessionController sc = SessionController.getInstance();
		JSONArray list;
		JSONObject jSon = new JSONObject();
		Usuario usuario = sc.getUser(key);
		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		List<UsuarioProj> uss = sbc.listarDevProj(usuario);
		
		list = new JSONArray(uss);
		jSon.put("devsProj", list);
		return Response.status(200).entity(jSon.toString()).build();
	}
	
	@Path("listarDevTreino")
	@POST
	@Produces("application/json")
	public Response listarDevTreino(@HeaderParam("key") String key) throws JSONException {
		SessionController sc = SessionController.getInstance();
		JSONArray list;
		JSONObject jSon = new JSONObject();
		Usuario usuario = sc.getUser(key);
		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		List<UsuarioTreino> uss = sbc.listarDevTreino(usuario);
		
		list = new JSONArray(uss);
		jSon.put("devsTreino", list);
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
	
	@Path("listarProjCpf")
	@POST
	@Produces("application/json")
	public Response listarProjCpf(@HeaderParam("cpf") long cpf, @HeaderParam("key") String key) throws JSONException {
		SessionController sc = SessionController.getInstance();
		JSONArray list;
		JSONObject jSon = new JSONObject();
		//Usuario usuario = sc.getUser(key);
		Usuario usuario = new Usuario();
		usuario.setCpf(cpf);
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
	
	@Path("exibirMetricas")
	@POST
	//@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response exibirMetricas(@HeaderParam("usuario") String usuario ,@HeaderParam("projeto") String projeto, @HeaderParam("key") String key ) throws JSONException {
		SessionController sc = SessionController.getInstance();
		JSONArray result;
		JSONObject jSonProjeto = new JSONObject(projeto);
		JSONObject jSonUsuario = new JSONObject(usuario);
		JSONObject jsonObject;
		
		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		
		Usuario u = sc.getUser(key);
		result = sbc.exibirMetricas( jSonUsuario, jSonProjeto, u );

		
		jsonObject = new JSONObject();
		jsonObject.put("result", result);
		

		return Response.status(200).entity(result.toString()).build();
		//return ;
	}
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	//@Consumes("multipart/form-data")
	
	public Response uploadFile(@FormDataParam("file") InputStream stream) {

		String uploadedFileLocation = "C://uploaded/" + stream.toString();


		// save the file to the server
//		writeToFile(uploadedInputStream, uploadedFileLocation);
		//System.out.println(theFile.toString());
		String output = "File saved to server location using FormDataMultiPart : " + uploadedFileLocation;
		System.out.println(stream.toString());
		return Response.status(200).entity(output).build();

	}
	
	@Path("cadastroTreino")
	@POST
	//@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastroTreino(@HeaderParam("treino") String treino, @HeaderParam("key") String key ) throws JSONException {
		SessionController sc = SessionController.getInstance();
		boolean result;
		JSONObject jsonObject = new JSONObject(treino);

		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		
		Usuario u = sc.getUser(key);
		result = sbc.cadastrarTreino(jsonObject, u );

		
		jsonObject = new JSONObject();
		jsonObject.put("result", result);
		

		return Response.status(200).entity(jsonObject.toString()).build();
		//return ;
	}
	
	@Path("editTreino")
	@POST
	//@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editTreino(@HeaderParam("usuario") String usuario, @HeaderParam("key") String key ) throws JSONException {
		SessionController sc = SessionController.getInstance();
		boolean result;
		JSONObject jsonObject = new JSONObject(usuario);
		JSONObject jsonResponse;
		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		
		Usuario u = sc.getUser(key);
		
		jsonResponse = sbc.editDev(jsonObject, u );

		return Response.status(200).entity(jsonResponse.toString()).build();
		//return ;
	}
	
	@Path("listarTreino")
	@POST
	@Produces("application/json")
	public Response listarTreino(@HeaderParam("key") String key) throws JSONException {
		SessionController sc = SessionController.getInstance();
		JSONArray list;
		JSONObject jSon = new JSONObject();
		Usuario usuario = sc.getUser(key);
		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		List<Treino> tss = sbc.listarTreino();
		
		list = new JSONArray(tss);
		jSon.put("treino", list);
		return Response.status(200).entity(jSon.toString()).build();
	}
	
	@Path("atribuirTreino")
	@POST
	//@GET
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response atribuirTreino(@HeaderParam("atribuicao") String atribuicao, @HeaderParam("key") String key ) throws JSONException {
		SessionController sc = SessionController.getInstance();
		boolean result;
		JSONObject a = new JSONObject(atribuicao);

		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		
		Usuario u = sc.getUser(key);
		result = sbc.atribuirTreino(a, key);

		
		a = new JSONObject();

		a.put("result", result);
		

		return Response.status(200).entity(a.toString()).build();
		//return ;
	}
	

}

