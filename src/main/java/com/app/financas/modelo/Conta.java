package com.app.financas.modelo;

import org.springframework.data.annotation.Id;

public class Conta {

	@Id
	private String id;
	private String nome;
	private TipoConta tipoConta;
	private Categoria categoria;



	public Conta(String nome, TipoConta tipoConta, Categoria categoria) {
		super();
		this.nome = nome;
		this.tipoConta = tipoConta;
		this.categoria = categoria;
	}

	public Conta() {
		// TODO Auto-generated constructor stub
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoConta getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoConta tipoConta) {
		this.tipoConta = tipoConta;
	}

}
