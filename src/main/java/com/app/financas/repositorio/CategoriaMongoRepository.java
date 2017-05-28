package com.app.financas.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.financas.modelo.Categoria;

public interface CategoriaMongoRepository extends MongoRepository<Categoria, String> {

}
