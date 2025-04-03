package com.doctor.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doctor.service.entities.Especialidad;
import com.doctor.service.repository.EspecialidadRepository;

@Service
public class EspecialidadService {

	@Autowired
	private EspecialidadRepository especialidadRepository;
	
	public List<Especialidad> findAllEspecialidad(){
		return especialidadRepository.findAll();
	}
	
	public Especialidad saveEspecialidad(Especialidad especialidad) {
		return especialidadRepository.save(especialidad);
	}
	
	public Optional<Especialidad> findEspecialidadById(Long id){
		return especialidadRepository.findById(id);
	}
	
	public void deleteEspecialidadById(Long id) {
		especialidadRepository.deleteById(id);
	}
}
