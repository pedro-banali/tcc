import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pucpr.br.bsi2015.tcc.selfiecode.controller.SelfieCodeBC;
import com.pucpr.br.bsi2015.tcc.selfiecode.dao.ProjetoDAO;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Usuario;
import com.pucpr.br.bsi2015.tcc.selfiecode.service.WebService;
import com.pucpr.br.bsi2015.tcc.selfiecode.session.SessionController;

public class Principal {
	static void teste()
	{
		tt.cancel();
		t.cancel();
		t = new Timer();
		
		start();
	}
	static int i = 0;
	static Timer t = new Timer();
	static TimerTask tt;
	static void start(){
		tt = new TimerTask() {
		    @Override
		    public void run() {
		        //do something
		    	System.out.println(i++);
		    	if(i > 10){i =0;teste();}
		    };
		};
		 
		t.schedule(tt,1000,1000);
	}
	public static void main(String[] args) {
		
		
		SessionController sc = SessionController.getInstance();
		JSONArray result;
		JSONObject jSonProjeto = new JSONObject("{proj:1}");
		JSONObject jSonUsuario = new JSONObject("{dev:22222222223}");
		JSONObject jsonObject;
		
		SelfieCodeBC sbc = SelfieCodeBC.getInstance();
		
		Usuario u = new Usuario();
		result = sbc.exibirMetricas( jSonUsuario, jSonProjeto, u );

		
		jsonObject = new JSONObject();
		jsonObject.put("result", result);
		

		//return Response.status(200).entity(result.toString()).build();
//		start();
//		String host = "127.0.0.1";
//
//		ServerSocket serverSocket;
//		try {
//			serverSocket = new ServerSocket(15123);
//
//			Socket socket = serverSocket.accept();
//
//			System.out.println("Accepted connection : " + socket);
//			File transferFile = new File("C:\\selfieCode\\test.txt");
//			byte[] bytearray = new byte[(int) transferFile.length()];
//			FileInputStream fin = new FileInputStream(transferFile);
//			BufferedInputStream bin = new BufferedInputStream(fin);
//			bin.read(bytearray, 0, bytearray.length);
//			OutputStream os = socket.getOutputStream();
//			System.out.println("Sending Files...");
//			os.write(bytearray, 0, bytearray.length);
//			os.flush();
//			socket.close();
//			System.out.println("File transfer complete");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
