package com.doctor.service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.doctor.service.entities.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{

	Page<Doctor> findAll(Pageable pageable);
	
	//Buscar doctor por apellido
	List<Doctor> findByApellido (String apellido);
}
