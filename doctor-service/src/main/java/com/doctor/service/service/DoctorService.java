package com.doctor.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.doctor.service.entities.Doctor;
import com.doctor.service.repository.DoctorRepository;

@Service
public class DoctorService {

	@Autowired
	private DoctorRepository doctorRepository;
	
	public List<Doctor> findAllDoctors(){
		return doctorRepository.findAll();
	}
	
	//Listar doctores por paginación
	public Page<Doctor> findDoctorByPagination(int page, int size){
		Pageable pageable = PageRequest.of(page, size);
		return doctorRepository.findAll(pageable);
	}
	
	public Doctor saveDoctor(Doctor doctor) {
		return doctorRepository.save(doctor);
	}
	
	public Optional<Doctor> findDoctorById(Long id){
		return doctorRepository.findById(id);
	}
	
	//Buscar doctor por apellido
	public List<Doctor> findDoctorByApellido(String apellido){
		return doctorRepository.findByApellido(apellido);
	}
	
	public void deleteDoctor(Long id) {
		doctorRepository.deleteById(id);
	}
}
