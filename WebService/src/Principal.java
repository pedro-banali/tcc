import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.pucpr.br.bsi2015.tcc.selfiecode.dao.ProjetoDAO;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Usuario;

public class Principal {
	public static void main(String[] args) {
		String host = "127.0.0.1";

		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(15123);

			Socket socket = serverSocket.accept();

			System.out.println("Accepted connection : " + socket);
			File transferFile = new File("C:\\selfieCode\\test.txt");
			byte[] bytearray = new byte[(int) transferFile.length()];
			FileInputStream fin = new FileInputStream(transferFile);
			BufferedInputStream bin = new BufferedInputStream(fin);
			bin.read(bytearray, 0, bytearray.length);
			OutputStream os = socket.getOutputStream();
			System.out.println("Sending Files...");
			os.write(bytearray, 0, bytearray.length);
			os.flush();
			socket.close();
			System.out.println("File transfer complete");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
