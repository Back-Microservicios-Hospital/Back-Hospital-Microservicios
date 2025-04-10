package com.historial.service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.historial.service.coleccion.HistorialMedico;

public interface HistorialMedicoRepository extends MongoRepository<HistorialMedico, String>{

	//Esto es para buscar historial con el dni del paciente(Es necesario la busqueda por id)
	List<HistorialMedico> findByPacienteId (Long pacienteId);
}
