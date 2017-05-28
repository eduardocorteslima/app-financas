package com.app.financas.modelo;

import org.springframework.data.annotation.Id;

public class Categoria {
	@Id
	private String id;
	private String nome;

	
	public Categoria() {
		// TODO Auto-generated constructor stub
	}

	public Categoria(String nome) {
		super();
		this.nome = nome;
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

}
