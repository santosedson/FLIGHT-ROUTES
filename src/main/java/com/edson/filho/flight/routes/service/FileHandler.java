package com.edson.filho.flight.routes.service;
/**
* @author  Edson Filho
* @version 1.0
* @since   2020-08-31 
*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.edson.filho.flight.routes.commons.Directory;
import com.edson.filho.flight.routes.data.FileLine;
import com.edson.filho.flight.routes.data.FileLines;



public class FileHandler {

	
	 /**
     * This function will deserialize the content of csv file and will put it in  FileLines Object
     * @param csv File 
     * @return FileLines object
     */
	public FileLines readFile(File file) throws Exception {
		
		ArrayList<FileLine> lineArrayList = new ArrayList<FileLine>();
		FileLines lines = new FileLines();
		InputStream inputStream = new FileInputStream(file.getAbsolutePath());
		InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		BufferedReader reader = new BufferedReader(streamReader);
		String lineArray[] = null;
		for (String fileLine; (fileLine = reader.readLine()) != null;) {
			lineArray = fileLine.split(",");
			lineArrayList.add(new FileLine(lineArray));
		}
		lines.setLines(lineArrayList);
		reader.close();
		if (lineArrayList.isEmpty() ) {
			return null;
		} else {
			return lines;
		}
	}
	
    /**
     * Given the path of the directory, this function will scan the dir looking for files
     * @param path
     * @return List of files (only csv files)
     */
	public List<File> scanFiles(String dir) {
		File files = new File(dir);
		File [] filesArray =files.listFiles();
		return getOnlyCsvFiles(filesArray);
	}
	
    /**
     * This function will make sure that only csv files will be returned to the caller
     * @param Array of Files 
     * @return List of csv files
     */
	private List<File> getOnlyCsvFiles(File [] filesArray) {
		List<File> csvFiles= new ArrayList<File>();
		if(filesArray!=null) {
			for(File file:filesArray) {
				String name = file.getName();
				int lastIndexOf = name.lastIndexOf(".");
			    if (lastIndexOf != -1) {
			    	if(name.substring(lastIndexOf).equalsIgnoreCase(Directory.FILE_TYPE.getCode())){
			    		csvFiles.add(file);
			    	}
			    }
			}
		 }
	    return csvFiles;
	}
	
    /**
     * This function will edit csv file writing a new line of routes
     * @param File name to edit
     * @param Formated routes to add "from,to,cost"
     * @return success(true), fail (false;)
     */
	public boolean editFile( String fileName, String newLine) {
		String absolutePath=Directory.DIR.getCode()+fileName+Directory.FILE_TYPE.getCode();
		boolean sucess;
		    PrintWriter pw = null;
		    File file = new File(absolutePath);
		    try {
		        if (file.exists()) {
		        	pw = new PrintWriter(new FileOutputStream(absolutePath, true));
			        pw.write("\r" + newLine);
			        sucess=true;
		        }else {
		        	//file.createNewFile();
		        	//pw = new PrintWriter(new FileOutputStream(fileName, true));
			        //pw.write("\r" + newLine);
		        	sucess=false;
		        }
		    } catch (IOException e) {
		    	System.out.println(e.getMessage());
		    	sucess=false;
		    } finally {
		        if (pw != null) {
		        	pw.flush();
		        	pw.close();
		        }
		    }
		    return sucess;
	}
	
}
