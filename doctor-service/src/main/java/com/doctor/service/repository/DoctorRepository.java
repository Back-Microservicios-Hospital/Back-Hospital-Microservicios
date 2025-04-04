package com.doctor.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.doctor.service.entities.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{

	Page<Doctor> findAll(Pageable pageable);
}
