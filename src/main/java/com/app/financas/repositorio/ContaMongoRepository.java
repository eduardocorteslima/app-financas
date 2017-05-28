package com.app.financas.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.financas.modelo.Conta;

public interface ContaMongoRepository extends MongoRepository<Conta, String> {

}
