package com.lonely.gson;

import java.util.List;

public class Result {

	private List<Agent> agent = null;

	private int code = 0;

	private String message = "";

	public List<Agent> getAgent() {
		return agent;
	}

	public void setAgent(List<Agent> agent) {
		this.agent = agent;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
