package com.cita.service.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cita.service.dto.CitaDetalleDTO;
import com.cita.service.dto.DoctorDTO;
import com.cita.service.dto.PacienteDTO;
import com.cita.service.entities.Cita;
import com.cita.service.feignclients.DoctorClient;
import com.cita.service.feignclients.PacienteClient;
import com.cita.service.repository.CitaRepository;

@Service
public class CitaService {

	private static Logger logger = LoggerFactory.getLogger(CitaService.class); 
	
	@Autowired
	private CitaRepository citaRepository;

	@Autowired
	private DoctorClient doctorClient;

	@Autowired
	private PacienteClient pacienteClient;

	public List<CitaDetalleDTO> findAllCitas() {
		
		List<Cita> citas = citaRepository.findAll();
		
		return citas.stream().map(cita -> {
			DoctorDTO doctor = doctorClient.getDoctorById(cita.getDoctorId());
			PacienteDTO paciente = pacienteClient.getPacienteById(cita.getPacienteId());
			
			CitaDetalleDTO detalleCita = new CitaDetalleDTO();
			detalleCita.setId(cita.getId());
			detalleCita.setFecha(cita.getFecha());
			detalleCita.setDoctor(doctor);
			detalleCita.setPaciente(paciente);
			detalleCita.setEstado(cita.getEstado());
			
			return detalleCita;
			
		}).collect(Collectors.toList());
	
	}

	public Cita saveCita(Cita cita) {

		try {
			
			DoctorDTO doctor = doctorClient.getDoctorById(cita.getDoctorId());
			
			if (doctor == null) {
				logger.error("Doctor no encontrado con el id: {}", cita.getDoctorId());
				throw new RuntimeException("Doctor no encontrado con ID: " + cita.getDoctorId());
			}
			
			PacienteDTO paciente = pacienteClient.getPacienteById(cita.getPacienteId());
			
			if (paciente == null) {
				logger.error("Paciente no encontrado con el id: {}", cita.getPacienteId());
				throw new RuntimeException("Paciente no encontrado con ID: " + cita.getPacienteId());
			}
			
			return citaRepository.save(cita);
			
		} catch (Exception e) {
			logger.error("Error en el service para crear una cita {}", e);
			throw new RuntimeException("No se crear la cita, error en el service" + e.getMessage());
		}

	}
	
	//Buscar paciente por dni
	public PacienteDTO findPacienteByDni(String dni) {
		
		try {
			
			PacienteDTO paciente = pacienteClient.getPacienteByDni(dni);
			
			if (paciente == null) {
				logger.error("Error, paciente no encontrado con el dni: {}", dni);
				throw new RuntimeException("Error, paciente no encontrado con el dni: " + dni);
			}
			
			return paciente;
			
		} catch (Exception e) {
			logger.error("Error, en el service de buscar paciente por el dni {}", e);
			throw new RuntimeException("Error, en el service de buscar paciente por el dni" + e.getMessage());
		}
	}

	public Optional<Cita> findCitaById(Long id) {
		return citaRepository.findById(id);
	}

	public void deleteCita(Long id) {
		citaRepository.deleteById(id);
	}
}
