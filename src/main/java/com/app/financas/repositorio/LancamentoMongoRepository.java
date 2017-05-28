package com.app.financas.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.financas.modelo.Lancamento;

public interface LancamentoMongoRepository extends MongoRepository<Lancamento, String> {

}
