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

	public String dicas(AbstractMetricSource c) throws Exception {
		Date data;
		SimpleDateFormat df;
		JSONObject json = new JSONObject();
		json.put("handle", c.getHandle());
		json.put("values", c.getValues());
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
			data = new Date();
			df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			lc.gerarLog("Valores Coletados: " + json.toString() + " Data e hora: " + df.format(data));
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
        	jsch.addIdentity("MIIEogIBAAKCAQEAikdgc7Nwlj7PEpxLsvLH+Ng8+Vn/ywJaQS7ZVkuLa023WsYYe5Z7TSw0FJJs8/qXxURuk9beOUiopttGPUBbofF3kvpiqthUwDEq67cXIcT8E1EsBK1D9JK0yz2qv9a3gGz7U028UpACqdF6jHxcPkJ8vE6YCsbpMcJZSeUXVGIUgczkJE9YTpoPTQU7UySw90MDnNL7PPqBOOVTeFZSrhx0Rsa5J5lCBJrlyFVqeLJqVEIdt+oXpQ8k9fmobRAyGpmtCoJ72avEn0qYzua/plBDRllD+7kZRb5f1yr54tKdpd64jqKpVCnUe76w6JGUokg1k/HLZKX6tJm7fmxhLwIDAQABAoIBAGPOZwC76KyZQw8UvtK3x7yGH2R/IFCgLxLTRM2zkzrtCkW7q9owYGjwWdrrYTasjjU1Unbk5NsF5a0hoc3+EOWPixYMIYFcybc7BdbC/TBDpQowUYxCn0T9Sv9TUFZNHX/VqYWUGzSgezulVkXmURIjHTMxqy5EKfbfZ2EduLwPSz1VMXqZnKKvE+Q7ALCuXGuxpKU+KjYgVYC2me6xrpgy48VyzX0FX8HIZu6LOzwGGx2/vjzKznn6SUAeEhixRKsfvu8hF7q80CmD8eIVVAhJEJdXsi9bPJYT3hzqVmkt/pebxfWWH3SLkDEmkKZjLxyiocY6+A3IJEHZOQtRmBkCgYEAwLSK69zFWzz7CaoEtHs2zQOZxIyz3gC3Wa7vJTq9uEKaflcuSIBggMKk9mKDdd+urf2zHrA09yKcMdLTqX2/0Wik8G3KrDyASW8w6NRCecuINRyhPTxXZSwHmTy+DsEygCMhGSbloH06tFkvc89C5i+qByusEBcqgXCCY0JTrhMCgYEAt7JvEuQWz5G5DNwnCdMO6dWSDIx6Rog8v62zjnD31zDnn63OGX1jaE53fhw+hQ6Cy8SuWlp0nY77+9UCZ+k9ZWrpWCglJ3dvXQquh34KvHFTBJC/2WESPvwdHTjfiSazG2AJmlqdM47CuN8pKmEY7BGdtU44hmhtXVctKoXTM/UCgYAsAA1IVZxqfL4FMuDoJMoafZv6mPXo1tkGjT7ljUgMbojAGD/lJgri2463Az9pBq3n5GmltC4jz69CYRUbOi82LWKb0zXXpejU26KbRvv2u1ZaajMWvFRoMWl6v7fVvp89Ssgf6hW8U1u4GjUPiF+KF5AHGx2qft+htoLPDzH0KQKBgDD7/+qFsB+BuFrZif9wrJVmQh/eheyw/6INa8gcD7rua5WE/2SErzFtWyfh2Doa+H3l4KhvXpF1Q3SQBKg45gpZTAgaDG5NxwCEjK3MyogdoAmjn8UTwY3SJOFZ/SHRlAlEvsrORwUsmHg76fpHEiJBQFDMnv4YcrFlQcIJFZzRAoGAERAhLI53ukpAKVGxjaUtDebfv+pNbdKeCw9fpBCepYz/wJuc2wlVC0NqAKE2h5bgMsKzglM2sq8Hbpy90dGbv+3EZds1cKUF9q0SIPblDl1SMiMCB72kYw5uJVT9cA3bbhDt6U6QxmaS02nIWmA0s0DnpeB2pXisIbEm9x1X4oU=");
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
