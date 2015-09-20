import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.pucpr.br.bsi2015.tcc.selfiecode.controller.MetricsController;

public class Principal {
	public static void main(String[] args) {
			try {
				//MetricsController.getInstance().uploadFile(null);
				
				String str = "C:/Desenvolvimento/runtime-EclipseApplication/Teste/src/teste.txt";
				int startIndex = str.indexOf("src/");
				
				//String replacement = "I AM JUST A REPLACEMENT";
				String toBeReplaced = str.substring(startIndex + 4, str.length());
				str = str.replace(toBeReplaced, "");
				System.out.println(str);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		

	}	
}
