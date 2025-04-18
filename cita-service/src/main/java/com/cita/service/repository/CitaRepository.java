package com.cita.service.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cita.service.entities.Cita;

public interface CitaRepository extends JpaRepository<Cita, Long>{

	//Buscar cita por dni de una paciente(Donde el id es necesario)
	List<Cita> findByPacienteId(Long pacienteId);
	
	//Buscar cita por fecha
	List<Cita> findByFecha(LocalDate fecha);
	
	//Buscar cita por apellido del Doctor (Donde el id es necesario)
	List<Cita> findByDoctorId(Long doctorId);
	
	//Buscar cita con paginacion
	Page<Cita> findAll (Pageable pageable);
	
	
}
