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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jdt.internal.ui.packageview.PackageFragmentRootContainer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.Workbench;
import org.json.JSONObject;
import org.osgi.framework.Bundle;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import net.sourceforge.metrics.core.sources.AbstractMetricSource;

public class MetricsController extends Observable {

	private static MetricsController mc = new MetricsController();

	private final String USER_AGENT = "Mozilla/5.0";
	private String session;
	private List<String> dicas = new ArrayList<String>();

	private MetricsController() {

	}

	public static MetricsController getInstance() {
		return mc;
	}

	public void requestWebServiceGet(String url) throws Exception {
		// String url =
		// "http://192.168.112.129:8080/WebService/selfieCode/service";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());
		JOptionPane.showMessageDialog(null, response.toString());

	}

	public boolean login(String usuario, String password) throws Exception {
		String result;
		// String url =
		// "http://192.168.112.129:8080/WebService/selfieCode/service/login?user="+usuario+"&pass="+password;
		String url = "http://localhost/WebService/selfieCode/service/login?user=" + usuario + "&pass=" + password;
		Date data;
		SimpleDateFormat df;
		String nome;
		String session;
		LogController lc;
		URL obj = new URL(url);

		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("POST");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());

		// result = response.toString();
		JSONObject jsonObject = new JSONObject(response.toString());
		result = jsonObject.getString("result");
		if (!result.equals("login inexistente")) {
			session = result;
			lc = LogController.getInstance();
			data = new Date();
			nome = jsonObject.getString("username");
			df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			lc.gerarLog("Usuário: " + nome + " logou " + df.format(data) + "\n");
			return true;

		} else
			return false;

	}

	public String dicas(AbstractMetricSource c, String fileName, String date) throws Exception {
		Date data;
		SimpleDateFormat df;
		JSONObject json = new JSONObject();
		json.put("handle", c.getPath());
		json.put("values", c.getValues());
		json.put("fileName", fileName);
		json.put("date", date);
		HttpClient client = new DefaultHttpClient();
		String line = "";
		String result = null;
		LogController lc = LogController.getInstance();

		HttpPost post = new HttpPost("http://localhost/WebService/selfieCode/service/dicas");
		//post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"))
		try {

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("json", json.toString()));
			nameValuePairs.add(new BasicNameValuePair("session", session));

			lc.gerarLog("Valores Coletados: " + json.toString() + " Data e hora: " + date);
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = client.execute(post);
			//response.setC
			post.completed();
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

			while ((line = rd.readLine()) != null) {
				result = line;
				//dicas.add(result.toString());
			}
			
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;

	}

	public String uploadFile(String path) throws Exception {
		//IWorkspace workspace = ResourcesPlugin.getWorkspace();  
		//workspace.getRoot().toString();
		//get location of workspace (java.io.File)  
		//File workspaceDirectory = workspace.getRoot().getLocation().toFile();
		
		File f = new File(path);
		JSch jsch = new JSch();
        Session session = null;
        try {
            //session = jsch.getSession("fileSaverFTP", "192.168.112.129", 22);
        	Bundle bundle = Platform.getBundle("net.sourceforge.metrics");
    		URL fileURL = bundle.getEntry("log/AWSKey.pem");
        	jsch.addIdentity(FileLocator.resolve(fileURL).toURI().toString());
        	session = jsch.getSession("ubuntu", "ec2-52-88-229-56.us-west-2.compute.amazonaws.com", 22);
            session.setConfig("StrictHostKeyChecking", "no");
            //session.setPassword("file@051526");
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.put(path, "www/" + f.getName());
            sftpChannel.exit();
            session.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();  
        } catch (SftpException e) {
            e.printStackTrace();
        }
		

		return null;

	}
		
	public List<String> getDicas() {
		return dicas;
	}

}
