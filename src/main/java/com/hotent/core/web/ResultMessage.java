package com.hotent.core.web;

import java.io.Serializable;
import net.sf.json.util.JSONStringer;

public class ResultMessage implements Serializable {
	private static final long serialVersionUID = -7102370526170507252L;
	public static final int Success = 1;
	public static final int Fail = 0;
	private int result = 1;
	private String message = "";
	private String cause = "";

	public ResultMessage() {
	}

	public ResultMessage(int result, String message) {
		this.result = result;
		this.message = message;
	}

	public ResultMessage(int result, String message, String cause) {
		this.result = result;
		this.message = message;
		this.cause = cause;
	}

	public int getResult() {
		return this.result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCause() {
		return this.cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String toString() {
		JSONStringer stringer = new JSONStringer();
		stringer.object();
		stringer.key("result");
		stringer.value((long) this.result);
		stringer.key("message");
		stringer.value(this.message);
		stringer.key("cause");
		stringer.value(this.cause);
		stringer.endObject();
		return stringer.toString();
	}
}