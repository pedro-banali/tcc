package com.pucpr.br.bsi2015.tcc.selfiecode.filecontroller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.pucpr.br.bsi2015.tcc.selfiecode.controller.LogController;

public class FileController {
	private String filePath = "";
	private LogController log = LogController.getInstance();
	
	public String saveZip(String src)
	{
		//String str = "C:/Desenvolvimento/runtime-EclipseApplication/Teste/src/teste.txt";
//		int startIndex = src.indexOf("src\\");
//		int t = 4;
//		if(startIndex < 0)
//		{
//			startIndex = src.indexOf("src");
//			if(startIndex < 0)
//				return;
//			t = 3;
//		}
//		//String replacement = "I AM JUST A REPLACEMENT";
//		String toBeReplaced;
//		
//		toBeReplaced = src.substring(startIndex + t, src.length());
//
//		
//		src = src.replace(toBeReplaced, "");
		
		File directoryToZip = new File(src);
		String fileName = "";
		List<File> fileList = new ArrayList<File>();
		try {
			System.out.println("---Getting references to all files in: " + directoryToZip.getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getAllFiles(directoryToZip, fileList);
		Date d = new Date();
		DateFormat df = new SimpleDateFormat("dd/mm/yyyy HH:mm:sss");
		log.gerarLog("---Creating zip file " + df.format(d));
		fileName = writeZipFile(directoryToZip, fileList);
		log.gerarLog("---Done " + df.format(d));
		return fileName;
		
	}

	public void getAllFiles(File dir, List<File> fileList) {
		Date d = new Date();
		DateFormat df = new SimpleDateFormat("dd/mm/yyyy HH:mm:sss");
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				fileList.add(file);
				if (file.isDirectory()) {
					log.gerarLog("directory: " + file.getCanonicalPath() + df.format(d));
					getAllFiles(file, fileList);
				} else {
					log.gerarLog("     file:" + file.getCanonicalPath() + df.format(d));
				}
			}
			
		} catch (IOException e) {
			log.gerarLog("ERRO " + e.getMessage() + df.format(d));
		}
	}
	

	public String writeZipFile(File directoryToZip, List<File> fileList) {
		String fullPath = "";
		String fileName = "";
		Date d = new Date();
		DateFormat df;
		try {
			String path = "C:\\selfiecode\\";
			
			df = new SimpleDateFormat("yyyy-mm-dd-HH-mm-sss");
			this.checkPath(path);
			fullPath = path + directoryToZip.getName() + df.format(d) + ".zip";
			fileName = directoryToZip.getName() + df.format(d) + ".zip";
			FileOutputStream fos = new FileOutputStream(fullPath);
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (File file : fileList) {
				if (!file.isDirectory()) { // we only zip files, not directories
					addToZip(directoryToZip, file, zos);
				}
			}

			zos.close();
			fos.close();
			df = new SimpleDateFormat("dd/mm/yyyy HH:mm:sss");
			this.filePath = fullPath;
			return fileName + "#:@:@:#" + df.format(d);
		} catch (FileNotFoundException e) {
			df = new SimpleDateFormat("dd/mm/yyyy HH:mm:sss");
			log.gerarLog("ERRO " + e.getMessage() + df.format(d));
		} catch (IOException e) {
			df = new SimpleDateFormat("dd/mm/yyyy HH:mm:sss");
			log.gerarLog("ERRO " + e.getMessage() + df.format(d));
		}
		return fileName + "#:@:@:#";
	}
	
	public void checkPath(String path)
	{
		File theDir = new File(path);
		Date d = new Date();
		DateFormat df;
		df = new SimpleDateFormat("dd/mm/yyyy HH:mm:sss");
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			log.gerarLog("Criando diretorio: " + path + df.format(d));
		    boolean result = false;

		    try{
		        theDir.mkdir();
		        result = true;
		    } 
		    catch(SecurityException se){
		    	log.gerarLog("ERRO: creating directory: C:\\selfiecode\\ " + se.getMessage());
		    }        
		    if(result) {    
		    	log.gerarLog("SUCCESS: DIR Criado");  
		    }
		}
	}
	
	public void addToZip(File directoryToZip, File file, ZipOutputStream zos) throws FileNotFoundException,
			IOException {
		Date d = new Date();
		DateFormat df;
		df = new SimpleDateFormat("dd/mm/yyyy HH:mm:sss");
		FileInputStream fis = new FileInputStream(file);

		// we want the zipEntry's path to be a relative path that is relative
		// to the directory being zipped, so chop off the rest of the path
		String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1,
				file.getCanonicalPath().length());
		log.gerarLog("Writing '" + zipFilePath + "' to zip file" + df.format(d));
		ZipEntry zipEntry = new ZipEntry(zipFilePath);
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}

		zos.closeEntry();
		fis.close();
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
}
