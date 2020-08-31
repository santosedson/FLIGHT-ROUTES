package com.edson.filho.flight.routes.data;
/**
* @author  Edson Filho
* @version 1.0
* @since   2020-08-31 
*/
import com.fasterxml.jackson.annotation.JsonProperty;

public class FileLine {

	@JsonProperty("from")
	private String from;

	@JsonProperty("to")
	private String to;

	@JsonProperty("cost")
	private String cost;

	public FileLine() {
		super();
	}

	public FileLine(String[] valores) {

		for (int i = 0; i < valores.length; i++) {
			switch (i) {
			case 0:
				this.from = valores[i];
				break;
			case 1:
				this.to = valores[i];
				break;
			case 2:
				this.cost = valores[i];
				break;
			}
		}
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

}
