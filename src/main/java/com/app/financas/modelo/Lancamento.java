package com.app.financas.modelo;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

public class Lancamento {

	@Id
	private String id;

	private String contaId;

	private double valor;
	private LocalDate data;

	public Lancamento() {
		// TODO Auto-generated constructor stub
	}

	public Lancamento(String conta, double valor, LocalDate data) {
		super();
		this.contaId = conta;
		this.valor = valor;
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getContaId() {
		return conta;
	}

	public void setContaId(String conta) {
		this.conta = conta;
	}

}
