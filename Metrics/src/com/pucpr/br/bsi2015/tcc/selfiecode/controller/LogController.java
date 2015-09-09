package com.pucpr.br.bsi2015.tcc.selfiecode.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class LogController {

	private static LogController instance = new LogController();

	private LogController() {

	}

	public static LogController getInstance() {
		return instance;
	}

	public boolean gerarLog(String text)
	{
		Bundle bundle = Platform.getBundle("net.sourceforge.metrics");
		URL fileURL = bundle.getEntry("log/log.txt");
		File file = null;
		try {
		    file = new File(FileLocator.resolve(fileURL).toURI());

		    String existente = loadFile();
		    
		    BufferedWriter writer = new BufferedWriter(new FileWriter( file)); 
		    writer.write(existente + text);
		    
		    writer.close(); 
 
		} catch (URISyntaxException e1) {
		    e1.printStackTrace();
		} catch (IOException e1) {
		    e1.printStackTrace();
		}
		return true;
	}

	public String loadFile() throws IOException, URISyntaxException {
		Bundle bundle = Platform.getBundle("net.sourceforge.metrics");
		URL fileURL = bundle.getEntry("log/log.txt");
		File file = null;

		file = new File(FileLocator.resolve(fileURL).toURI());

		StringBuffer buffer = new StringBuffer();
		FileInputStream stream = new FileInputStream(file);

		InputStreamReader streamReader = new InputStreamReader(stream);
		BufferedReader reader = new BufferedReader(streamReader);

		// Neste while eh lido o arquivo linha a linha
		String line = null;
		while ((line = reader.readLine()) != null) {
			buffer.append(line + "\n");
			// System.out.println(line);
		}

		// fechando os objetos de io:
		reader.close();
		streamReader.close();
		stream.close();
		return buffer.toString();
	}

}
