package com.edson.filho.flight.routes.data;
/**
* @author  Edson Filho
* @version 1.0
* @since   2020-08-31 
*/
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileLines {
	@JsonProperty("route")
	List<FileLine> lines;
	String fileName;

	public List<FileLine> getLines() {
		return lines;
	}

	public void setLines(List<FileLine> liles) {
		this.lines = liles;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
