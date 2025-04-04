package com.cita.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cita.service.entities.Estado;
import com.cita.service.repository.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Estado> findAllEstados(){
		return estadoRepository.findAll();
	}
	
	public Estado saveEstado(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public Optional<Estado> findEstadoById(Long id){
		return estadoRepository.findById(id);
	}
	
	public void deleteEstado (Long id) {
		estadoRepository.deleteById(id);
	}
}
