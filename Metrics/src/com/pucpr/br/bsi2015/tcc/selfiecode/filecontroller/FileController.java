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

public class FileController {
	private static FileController fc = new FileController();
	
	public static FileController getInstance()
	{
		return fc;
	}
	
	public void saveZip(String src)
	{
		//String str = "C:/Desenvolvimento/runtime-EclipseApplication/Teste/src/teste.txt";
		int startIndex = src.indexOf("src\\");
		int t = 4;
		if(startIndex < 0)
		{
			startIndex = src.indexOf("src");
			if(startIndex < 0)
				return;
			t = 3;
		}
		//String replacement = "I AM JUST A REPLACEMENT";
		String toBeReplaced;
		
		toBeReplaced = src.substring(startIndex + t, src.length());

		
		src = src.replace(toBeReplaced, "");
		
		File directoryToZip = new File(src);

		List<File> fileList = new ArrayList<File>();
		try {
			System.out.println("---Getting references to all files in: " + directoryToZip.getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getAllFiles(directoryToZip, fileList);
		System.out.println("---Creating zip file");
		writeZipFile(directoryToZip, fileList);
		System.out.println("---Done");
	}

	public void getAllFiles(File dir, List<File> fileList) {
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				fileList.add(file);
				if (file.isDirectory()) {
					System.out.println("directory:" + file.getCanonicalPath());
					getAllFiles(file, fileList);
				} else {
					System.out.println("     file:" + file.getCanonicalPath());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeZipFile(File directoryToZip, List<File> fileList) {

		try {
			Date d = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-mm-dd-HH-mm-sss");
			FileOutputStream fos = new FileOutputStream("C:\\selfiecode\\"+directoryToZip.getName() + df.format(d) + ".zip");
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (File file : fileList) {
				if (!file.isDirectory()) { // we only zip files, not directories
					addToZip(directoryToZip, file, zos);
				}
			}

			zos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addToZip(File directoryToZip, File file, ZipOutputStream zos) throws FileNotFoundException,
			IOException {

		FileInputStream fis = new FileInputStream(file);

		// we want the zipEntry's path to be a relative path that is relative
		// to the directory being zipped, so chop off the rest of the path
		String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1,
				file.getCanonicalPath().length());
		System.out.println("Writing '" + zipFilePath + "' to zip file");
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
}
