package com.pucpr.br.bsi2015.tcc.selfiecode.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.framework.Bundle;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.ProjectTime;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.*;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sourceforge.metrics.core.sources.AbstractMetricSource;

public class MetricsController extends Observable {

	private static MetricsController mc = new MetricsController();

	private final String USER_AGENT = "Mozilla/5.0";
	private String session;
	private List<String> dicas = new ArrayList<String>();
	private ProjectTime pr;
	private List<Thread> threads = new ArrayList<Thread>();
	private IJavaProject projeto;

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

	public int login(String usuario, String password) throws Exception {
		String result;
		// String url =
		// "http://192.168.112.129:8080/WebService/selfieCode/service/login?user="+usuario+"&pass="+password;
		String url = "http://localhost/WebService/selfieCode/service/login?user=" + usuario + "&pass=" + password;
		Date data;
		SimpleDateFormat df;
		String nome;
		LogController lc;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		SessionController sc = SessionController.getInstance();
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
		JSONArray projetos = new JSONArray();
		result = jsonObject.getString("result");
		if (!result.equals("login inexistente")) {

			lc = LogController.getInstance();
			data = new Date();
			nome = jsonObject.getString("username");
			df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			if (jsonObject.getInt("tipo") <= 2)
				return 2;
			sc.getSession().setUserName(nome);
			sc.getSession().setUserId(jsonObject.getInt("tipo"));
			sc.getSession().setSessionId(result);
			projetos = jsonObject.getJSONArray("projetos");
			for (int i = 0; i < projetos.length(); i++) {
				sc.getSession().getProjetos().put(projetos.getJSONObject(i).getInt("codigoProj"), new ProjectTime(
						projetos.getJSONObject(i).getString("nome"), projetos.getJSONObject(i).getInt("tempoColeta")));
				this.lerXml(projetos.getJSONObject(i).getString("nome"));
			}

			lc.gerarLog("Usuário: " + nome + " logou " + df.format(data) + "\n");
			return 0;

		} else
			return 1;

	}

	public void lerXml(String nomeProjeto) {
		// get object which represents the workspace
		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		// get location of workspace (java.io.File)
		File workspaceDirectory = workspace.getRoot().getLocation().toFile();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File(workspaceDirectory.getPath() + "/" + nomeProjeto + "/.project"));
			NodeList n = doc.getElementsByTagName("name");

			if (n.item(0).getTextContent().equals(nomeProjeto)) {
				for (int i = 1; i < n.getLength(); i++) {
					if (n.item(i).getTextContent().equals("net.sourceforge.metrics.builder"))
						return;
				}
			}
			NodeList n2 = doc.getElementsByTagName("buildSpec");
			Element buildCommand = doc.createElement("buildCommand");
			Element name = doc.createElement("name");
			name.setTextContent("net.sourceforge.metrics.builder");
			Element arguments = doc.createElement("arguments");
			buildCommand.appendChild(name);
			buildCommand.appendChild(arguments);

			n2.item(0).appendChild(buildCommand);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = transformerFactory.newTransformer();

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(
					new File(workspaceDirectory.getPath() + "/" + nomeProjeto + "/.project"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO: handle exception
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String dicas(AbstractMetricSource c, String fileName, String date, IJavaElement currentElm)
			throws Exception {
		Date data;
		SimpleDateFormat df;
		JSONObject json = new JSONObject();

		json.put("handle", c.getPath());
		json.put("values", c.getValues());
		json.put("fileName", fileName);
		json.put("date", date);
		String projeto;
		int r = 0;
		Integer key;
		ProjectTime value;
		SessionController sc = SessionController.getInstance();
		Iterator entries = sc.getSession().getProjetos().entrySet().iterator();
		json.put("sessionId", sc.getSession().getSessionId());
		while (entries.hasNext()) {
			Entry thisEntry = (Entry) entries.next();
			key = (Integer) thisEntry.getKey();
			value = (ProjectTime) thisEntry.getValue();
			if (currentElm.getJavaProject().getElementName().equals(value.getNomeProjeto())) {
				r = 1;
				json.put("projId", key);
			}
			// ...
		}
		// for (int i = 0; i < sc.getSession().getProjetos().size(); i++) {
		// projeto = sc.getSession().getProjetos().get(i);
		// if (currentElm.getJavaProject().getElementName().equals(projeto)) {
		// r = 1;
		// }
		// }
		if (r == 0)
			return "naoachou";

		json.put("projeto", currentElm.getJavaProject().getElementName());
		HttpClient client = new DefaultHttpClient();
		String line = "";
		String result = null;
		LogController lc = LogController.getInstance();

		HttpPost post = new HttpPost("http://localhost/WebService/selfieCode/service/dicas");
		// post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"))
		try {

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("json", json.toString()));
			nameValuePairs.add(new BasicNameValuePair("session", session));

			lc.gerarLog("Valores Coletados: " + json.toString() + " Data e hora: " + date);
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = client.execute(post);
			// response.setC
			post.completed();
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

			while ((line = rd.readLine()) != null) {
				result = line;
				// dicas.add(result.toString());
			}

			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;

	}

	public String uploadFile(String path) throws Exception {
		// IWorkspace workspace = ResourcesPlugin.getWorkspace();
		// workspace.getRoot().toString();
		// get location of workspace (java.io.File)
		// File workspaceDirectory = workspace.getRoot().getLocation().toFile();

		File f = new File(path);
		JSch jsch = new JSch();
		Session session = null;
		try {
			// session = jsch.getSession("fileSaverFTP", "192.168.112.129", 22);
			Bundle bundle = Platform.getBundle("net.sourceforge.metrics");
			URL fileURL = bundle.getEntry("log/AWSKey.pem");
			jsch.addIdentity(FileLocator.resolve(fileURL).getPath());
			session = jsch.getSession("ubuntu", "ec2-52-88-229-56.us-west-2.compute.amazonaws.com", 22);
			session.setConfig("StrictHostKeyChecking", "no");
			// session.setPassword("file@051526");
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

	public void setProject(IJavaProject projeto) {
		this.projeto = projeto;
	}

	public void addDicas(String dica) {

		dicas.add(dica);

		// dicas.add(dica);

	}

	public List<String> getDicas() {
		return dicas;
	}

	public List<Thread> getThreads() {
		return threads;
	}

	public boolean addThreads(Thread e) {
		return threads.add(e);
	}

	public void setThreads(List<Thread> threads) {
		this.threads = threads;
	}

	public void setPr(ProjectTime pr) {
		// TODO Auto-generated method stub
		this.pr = pr;
		Timer t = new Timer();
		TimerTask ts = new TimerTask() {

			@Override
			public void run() {
				// your code here, and if you have to refresh UI put this code:
				for (Thread t : threads) {
					if (!t.isAlive()) {
						try {
							t.join();
//							String dicaI, dicaJ;
//							boolean achou = false;
//							for (int i = 0; i <  dicas.size(); i++) {
//								dicaI = dicas.get(i);
//								achou = false;
//								for (int j = i; j <  dicas.size(); j++) {
//									dicaJ = dicas.get(j);
//									if (dicaI.equals(dicaJ)) {
//										achou = true;
//									}
//								}
//								if(achou != true)
//								writeMarkers(projeto, dicaI);
//							}
//							for (String dica : dicas)
//								writeMarkers(projeto, dica);
//
//							dicas.clear();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				pr.restart();
				
				

				threads = new ArrayList<Thread>();
				// this.cancel();
			}
		};
		t.schedule(ts, 5000);

	}

	private void writeMarkers(IJavaProject type, String message) {
		try {
			IResource resource = type.getUnderlyingResource();
			IMarker marker = resource.createMarker(IMarker.PROBLEM);
			marker.setAttribute(IMarker.MESSAGE, message);
		
			// marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_NORMAL);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
