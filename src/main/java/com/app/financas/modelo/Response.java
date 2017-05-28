package com.app.financas.modelo;

import java.io.Serializable;

public class Response<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private int statusCode;
	private String msg;
	private T itens;

	public Response(int statusCode, String msg, T itens) {
		super();
		this.statusCode = statusCode;
		this.msg = msg;
		this.itens = itens;
	}

	public Response() {
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getItens() {
		return itens;
	}

	public void setItens(T itens) {
		this.itens = itens;
	}

}
