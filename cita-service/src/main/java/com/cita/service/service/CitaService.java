package com.cita.service.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cita.service.dto.CitaDetalleDTO;
import com.cita.service.dto.CitaNuevaNotificacionRequest;
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
	
	@Autowired
	private CitaProducer citaProducer;

	public List<CitaDetalleDTO> findAllCitas() {
		
		List<Cita> citas = citaRepository.findAll();
		
		return citas.stream().map(cita -> {
			DoctorDTO doctor = doctorClient.getDoctorById(cita.getDoctorId());
			PacienteDTO paciente = pacienteClient.getPacienteById(cita.getPacienteId());
			
			CitaDetalleDTO detalleCita = new CitaDetalleDTO();
			detalleCita.setId(cita.getId());
			detalleCita.setFecha(cita.getFecha());
			detalleCita.setHora(cita.getHora());
			detalleCita.setDoctor(doctor);
			detalleCita.setPaciente(paciente);
			detalleCita.setEstado(cita.getEstado());
			
			return detalleCita;
			
		}).collect(Collectors.toList());
	
	}

	public Cita saveCita(Cita cita) {

		try {
			
			/*
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
			*/
			
			Cita citaGuardada = citaRepository.save(cita);
			
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
			
			//Aca capturamos los valores para enviar la notificación de una nueva cita
			CitaNuevaNotificacionRequest citaNotificacion = new CitaNuevaNotificacionRequest();
			citaNotificacion.setPacienteId(cita.getPacienteId());
			citaNotificacion.setDoctorAsignado(doctor.getApellido());
			citaNotificacion.setFechaCita(cita.getFecha());
			citaNotificacion.setHora(cita.getHora());
			citaNotificacion.setEstadoCita(cita.getEstado().getNombre());
			
			citaProducer.enviarNotificacionNuevaCita(citaNotificacion);
					
			return citaGuardada;
			
			
		} catch (Exception e) {
			logger.error("Error en el service para crear una cita {}", e);
			throw new RuntimeException("No se crear la cita, error en el service" + e.getMessage());
		}

	}
	
	//Buscar cita por fecha
	public List<CitaDetalleDTO> findCitaByFecha (LocalDate fecha){
		
		try {
			
			List<Cita> citas = citaRepository.findByFecha(fecha);
			
			return citas.stream().map(cita -> {
				DoctorDTO doctor = doctorClient.getDoctorById(cita.getDoctorId());
				PacienteDTO paciente = pacienteClient.getPacienteById(cita.getPacienteId());
				
				CitaDetalleDTO detalleCita = new CitaDetalleDTO();
				detalleCita.setId(cita.getId());
				detalleCita.setFecha(cita.getFecha());
				detalleCita.setHora(cita.getHora());
				detalleCita.setDoctor(doctor);
				detalleCita.setPaciente(paciente);
				detalleCita.setEstado(cita.getEstado());
				
				
				logger.info("Fecha ingresada: {} - Citas encontradas: {}", fecha, detalleCita);
				return detalleCita;
				
			}).collect(Collectors.toList());
			
		} catch (Exception e) {
			logger.error("Error desde el Service al buscar cita por fecha {}", e);
			throw new RuntimeException("Error desde el Service al buscar cita por fecha " + e.getMessage());
		}
	}
	
	//Buscar cita por dni del paciente
	public List<CitaDetalleDTO> findCitaByPacienteDni(String dni){
		
		try {
			
			PacienteDTO paciente = pacienteClient.getPacienteByDni(dni);
			
			if (paciente == null) {
				logger.error("Error, paciente no encontrado con el dni: {}", dni);
				throw new RuntimeException("Error, paciente no encontrado con el dni: " + dni);
			}
			
			List<Cita> listCita = citaRepository.findByPacienteId(paciente.getId());
			
			if (listCita.isEmpty()) {
				logger.error("El paciente con el dni: {}, no tiene citas", dni);
				throw new RuntimeException("No hay cita registrada con el DNI del paciente: " + dni);
			}
			
			return listCita.stream().map(citas -> {
				//Buscando al doctor por el id
				DoctorDTO doctor = doctorClient.getDoctorById(citas.getDoctorId());
				
				CitaDetalleDTO citaDetalle =new CitaDetalleDTO();
				citaDetalle.setId(citas.getId());
				citaDetalle.setFecha(citas.getFecha());
				citaDetalle.setHora(citas.getHora());
				citaDetalle.setDoctor(doctor);
				citaDetalle.setPaciente(paciente);
				citaDetalle.setEstado(citas.getEstado());
				
				logger.info("El paciente con el dni {} tiene una cita: {}", dni,citaDetalle);
				return citaDetalle;
			}).collect(Collectors.toList());
			
		} catch (Exception e) {
			logger.error("Error, al buscar una cita con el dni de un paciente: {}", dni, e);
			throw new RuntimeException("Error, al buscar una cita con el dni de un paciente: " + e.getMessage());
		}
	}
	
	//Buscar cita por apellido del doctor
	public List<CitaDetalleDTO> findCitaByDoctorApellido(String apellido){
		
		try {
			
			List<DoctorDTO> doctores = doctorClient.getDoctorByApellido(apellido);
			
			if (doctores == null) {
				logger.error("Error, no hay cita con el apellido del doctor: {}", apellido);
				throw new RuntimeException("Error, no hay cita con el apellido del doctor " + apellido);
			}
			
			//Esto lo hago porque cuando se busca un doctor por apellido, este devuelve una lista de doctores
			List<CitaDetalleDTO> resultado = new ArrayList<>();

	        for (DoctorDTO doctor : doctores) {
	            List<Cita> citas = citaRepository.findByDoctorId(doctor.getId());

	            for (Cita cita : citas) {
	                PacienteDTO paciente = pacienteClient.getPacienteById(cita.getPacienteId());

	                CitaDetalleDTO detalleCita = new CitaDetalleDTO();
	                detalleCita.setId(cita.getId());
	                detalleCita.setFecha(cita.getFecha());
	                detalleCita.setHora(cita.getHora());
	                detalleCita.setDoctor(doctor); // Ahora sí, doctor individual
	                detalleCita.setPaciente(paciente);
	                detalleCita.setEstado(cita.getEstado());

	                logger.info("Apellido del doctor: {} | Cita encontrada: {}", apellido, detalleCita);
	                resultado.add(detalleCita);
	            }
	        }
	        return resultado;
			
		} catch (Exception e) {
			logger.error("Error, al buscar una cita con el apellido del doctor: {}", apellido, e);
			throw new RuntimeException("Error, al buscar una cita con el apellido del doctor" + e.getMessage());
		}
	}
	
	/*
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
*/
	public Optional<Cita> findCitaById(Long id) {
		return citaRepository.findById(id);
	}

	public void deleteCita(Long id) {
		citaRepository.deleteById(id);
	}
}
