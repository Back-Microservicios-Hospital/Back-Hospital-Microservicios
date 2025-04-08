package com.historial.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.historial.service.coleccion.HistorialMedico;

public interface HistorialMedicoRepository extends MongoRepository<HistorialMedico, String>{

}
