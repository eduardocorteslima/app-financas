package com.app.financas.modelo;

public enum TipoConta {
	DEBITO(0), CREDITO(1);

	private int idTipo;

	TipoConta(int idTipo) {
		this.idTipo = idTipo;
	}
	 public static TipoConta get(int id) {
         for (TipoConta atual : values()) {
             if (atual.idTipo == id) {
                 return atual;
             }
         }
         throw new IllegalArgumentException("Tipo inválido: " + id);
     }
	public int getValor() {
		return idTipo;
	}
}
