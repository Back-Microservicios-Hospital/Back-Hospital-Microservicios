package com.historial.service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.historial.service.coleccion.HistorialMedico;
import com.historial.service.dto.DoctorDTO;
import com.historial.service.dto.HistorialMedicoDetalleDTO;
import com.historial.service.dto.PacienteDTO;
import com.historial.service.feignclients.DoctorClient;
import com.historial.service.feignclients.PacienteClient;
import com.historial.service.repository.HistorialMedicoRepository;

@Service
public class HistorialMedicoService {

	private static Logger logger = LoggerFactory.getLogger(HistorialMedicoService.class);
	
	@Autowired
	private HistorialMedicoRepository historialMedicoRepository;
	
	@Autowired
	private DoctorClient doctorClient;
	
	@Autowired
	private PacienteClient pacienteClient;
	
	public List<HistorialMedicoDetalleDTO> findAllHistoriales(){
		
		List<HistorialMedico> historiales = historialMedicoRepository.findAll();
		
		return historiales.stream().map(historial -> {
			DoctorDTO doctor = doctorClient.getDoctorById(historial.getDoctorId());
			PacienteDTO paciente = pacienteClient.getPacienteById(historial.getPacienteId());
			
			HistorialMedicoDetalleDTO historialDetalle = new HistorialMedicoDetalleDTO();
			historialDetalle.setId(historial.getId());
			historialDetalle.setDiagnostico(historial.getDiagnostico());
			historialDetalle.setReceta(historial.getReceta());
			historialDetalle.setDoctor(doctor);
			historialDetalle.setPaciente(paciente);
			
			return historialDetalle;
		}).collect(Collectors.toList());
	}
	
	public HistorialMedico saveHistorial (HistorialMedico historial) {
		
		try {
			
			DoctorDTO doctor = doctorClient.getDoctorById(historial.getDoctorId());
			
			if (doctor == null) {
				logger.error("Error al no encontrar al doctor");
				throw new RuntimeException("Doctor no encontrado");
			}
			
			PacienteDTO paciente = pacienteClient.getPacienteById(historial.getPacienteId());
			
			if (paciente == null) {
				logger.error("Error al no encontrar al paciente");
				throw new RuntimeException("Error, paciente no encontrado");
			}
			
			return historialMedicoRepository.save(historial);
			
		} catch (Exception e) {
			logger.error("Error, al crear el historial médico desde el service {}", e);
			throw new RuntimeException("No se creo el historial médico" + e);
		}
		
	}
	
	//Buscar historial médido con el dni del paciente
	public List<HistorialMedicoDetalleDTO> getHistorialByPacienteDni(String dni){
		
		try {
			
			PacienteDTO paciente = pacienteClient.getPacienteByDni(dni);
			
			if (paciente == null) {
				logger.error("Error, no hay paciente con el dni: {}", dni);
				throw new RuntimeException("Error, con el dni ingresado: " + dni);
			}
			
			List<HistorialMedico> historiales = historialMedicoRepository.findByPacienteId(paciente.getId());
			
			if (historiales.isEmpty()) {
				logger.error("El paciente con el DNI: {}, no tiene historial médico", dni);
				throw new RuntimeException("El paciente no cuenta con historial médico");
			}
			
			
			return historiales.stream().map(historial -> {
				//Hacemos la busqueda del Doctor tambien
				DoctorDTO doctor = doctorClient.getDoctorById(historial.getDoctorId());
				
				HistorialMedicoDetalleDTO historialDTO = new HistorialMedicoDetalleDTO();
				historialDTO.setId(historial.getId());
				historialDTO.setDiagnostico(historial.getDiagnostico());
				historialDTO.setReceta(historial.getReceta());
				historialDTO.setDoctor(doctor);
				historialDTO.setPaciente(paciente);
				
				logger.info("El paciente con el dni: {}, tiene el historial: {}", dni, historialDTO);
				return historialDTO;
			}).collect(Collectors.toList());
			
		} catch (Exception e) {
			logger.error("Error desde el ServiceHistorial, al obtener historial por DNI del paciente: {}", e);
			throw new RuntimeException("Error desde el ServiceHistorial, al obtener historial por DNI del paciente: " + e.getMessage());
		}
	}
	
	public Optional<HistorialMedico> findHistorialById (String id){
		return historialMedicoRepository.findById(id);
	}
	
	public void deleteHistorial (String id) {
		historialMedicoRepository.deleteById(id);
	}
}
