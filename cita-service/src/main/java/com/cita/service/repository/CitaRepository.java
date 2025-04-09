package com.cita.service.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cita.service.entities.Cita;

public interface CitaRepository extends JpaRepository<Cita, Long>{

	//Buscar cita por dni de una paciente(Donde el id es necesario)
	List<Cita> findByPacienteId(Long pacienteId);
	
	//Buscar cita por fecha
	List<Cita> findByFecha(LocalDate fecha);
	
}
