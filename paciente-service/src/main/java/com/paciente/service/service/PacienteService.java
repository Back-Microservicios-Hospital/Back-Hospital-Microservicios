package com.paciente.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.paciente.service.entities.Paciente;
import com.paciente.service.repository.PacienteRepository;

@Service
public class PacienteService {

	@Autowired
	private PacienteRepository pacienteRepository;
	
	public List<Paciente> findAllPacientes(){
		return pacienteRepository.findAll();
	}
	
	//Listar con paginacion
	public Page<Paciente> getPacienteByPage(int page, int size){
		Pageable pageable = PageRequest.of(page, size);
		return pacienteRepository.findAll(pageable);
	}
	
	public Paciente savePaciente(Paciente paciente) {
		return pacienteRepository.save(paciente);
	}
	
	public Optional<Paciente> findPacienteById(Long id){
		return pacienteRepository.findById(id);
	}
	
	//Buscar paciente por Apellido
	public List<Paciente> findPacienteByApelllido(String apellido){
		return pacienteRepository.findByApellido(apellido);
	}
	
	//Buscar paciente por dni
	public Paciente findPacienteByDni(String dni) {
		return pacienteRepository.findByDni(dni);
	}
	
	public void deletePaciente(Long id) {
		pacienteRepository.deleteById(id);
	}
}
