package com.cita.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cita.service.entities.Cita;

public interface CitaRepository extends JpaRepository<Cita, Long>{

	//Buscar cita por apellido de una paciente
	List<Cita> findByPacienteId(Long pacienteId);
}
