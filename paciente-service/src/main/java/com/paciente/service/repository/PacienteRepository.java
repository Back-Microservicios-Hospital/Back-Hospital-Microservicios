package com.paciente.service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.paciente.service.entities.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{

	Page<Paciente> findAll (Pageable pageable);
	
	//Buscar paciente por apellido
	List<Paciente> findByApellido(String apellido);
	
	//Buscar paciente por dni
	Paciente findByDni (String dni);
}
