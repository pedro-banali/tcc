package com.pucpr.br.bsi2015.tcc.selfiecode.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

import javax.swing.JOptionPane;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import net.sourceforge.metrics.core.sources.AbstractMetricSource;

public class MetricsController extends Observable {
	
	private static MetricsController mc = new MetricsController();
	
	private final String USER_AGENT = "Mozilla/5.0";
	private String session;
	private List<String> dicas = new ArrayList<String>();
	
	
	private MetricsController()
	{
		
	}
	
	public static MetricsController getInstance()
	{
		return mc;
	}
	
	public void requestWebServiceGet(String url) throws Exception
	{
		//String url = "http://192.168.112.129:8080/WebService/selfieCode/service";
		 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
		JOptionPane.showMessageDialog(null, response.toString());
 
	}
	
	public boolean login(String usuario, String password) throws Exception
	{
		String result;
		//String url = "http://192.168.112.129:8080/WebService/selfieCode/service/login?user="+usuario+"&pass="+password;
		String url = "http://localhost/WebService/selfieCode/service/login?user="+usuario+"&pass="+password;
		Date data ;
		SimpleDateFormat df;
		String nome;
		String session;
		LogController lc;
		URL obj = new URL(url);
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("POST");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
		
//		result = response.toString();
		JSONObject jsonObject = new JSONObject(response.toString());
		result = jsonObject.getString("result");
		if(!result.equals("login inexistente"))
		{
			session = result;
			lc = LogController.getInstance();
			data = new Date();
			nome = jsonObject.getString("username");
			df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			lc.gerarLog("Usu�rio: " + nome + " logou " + df.format(data) + "\n");
			return true;
			
		}
		else			
			return false;
 
	}
	
	public String dicas(AbstractMetricSource c) throws Exception
	{
		Date data ;
		SimpleDateFormat df;
		JSONObject json = new JSONObject();
		json.put("handle", c.getHandle());
		json.put("values", c.getValues());
		HttpClient client = new DefaultHttpClient();
		String line = "";
		String result = "";
		LogController lc = LogController.getInstance();
		
		HttpPost post = new HttpPost("http://localhost/WebService/selfieCode/service/dicas");
		 try {

		      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		      nameValuePairs.add(new BasicNameValuePair("json", json.toString()));
		      nameValuePairs
		          .add(new BasicNameValuePair("session", session));
		      data = new Date();
		      df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		      
		      lc.gerarLog("Valores Coletados: " + json.toString() + " Data e hora: " + df.format(data));
		      post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		      HttpResponse response = client.execute(post);
		      post.completed();
		      BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		     
		      while ((line = rd.readLine()) != null) {
		    	  result = line;
		      }
		      dicas.add(result); 
		      rd.close();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		
		return result;
 
	}
	
	public String uploadFile(File f) throws Exception
	{
		Date data ;
		SimpleDateFormat df;
		JSONObject json = new JSONObject();
	
		HttpClient client = new DefaultHttpClient();
		String line = "";
		String result = "";
		LogController lc = LogController.getInstance();
		
		HttpPost post = new HttpPost("http://localhost/WebService/selfieCode/service/upload");
		MultipartEntity reqEntity = new MultipartEntity();

		 reqEntity.addPart("file", new FileBody(new File("C:\\Users\\pedro-banali\\Downloads\\Titulo_13758581.pdf")));

		 post.setEntity(reqEntity);
		 
		 HttpResponse response = client.execute(post);
	     post.completed();
	     BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	     
	     return result;
 
	}
	
	public List<String> getDicas()
	{
		return dicas;
	}

}
