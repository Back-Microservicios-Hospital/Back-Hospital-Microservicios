package com.notificacion.service.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notificacion.service.coleccion.Notificacion;
import com.notificacion.service.dto.CitaNuevaNotificacionResponde;
import com.notificacion.service.dto.NotificacionDetalleDTO;
import com.notificacion.service.dto.PacienteDTO;
import com.notificacion.service.feignclients.PacienteClient;
import com.notificacion.service.repository.NotificacionRepository;

@Service
public class NotificacionService {

	private static Logger logger = LoggerFactory.getLogger(NotificacionService.class);
	
	@Autowired
	private NotificacionRepository notificacionRepository;
	
	@Autowired
	private PacienteClient pacienteClient;
	
	public List<NotificacionDetalleDTO> findAllNotificaciones(){
		
		List<Notificacion> notificaciones = notificacionRepository.findAll();
		
		return notificaciones.stream().map(notificacion -> {
			
			PacienteDTO paciente = pacienteClient.getPacienteById(notificacion.getPacienteId());
			
			NotificacionDetalleDTO notificacionDetalle = new NotificacionDetalleDTO();
			notificacionDetalle.setId(notificacion.getId());
			notificacionDetalle.setTitulo(notificacion.getTitulo());
			notificacionDetalle.setMensaje(notificacion.getMensaje());
			notificacionDetalle.setFecha(notificacion.getFecha());
			notificacionDetalle.setFechaCita(notificacion.getFechaCita());
			notificacionDetalle.setEstadoCita(notificacion.getEstadoCita());
			notificacionDetalle.setNombreDoctor(notificacion.getNombreDoctor());
			notificacionDetalle.setPaciente(paciente);
			
			logger.info("Listar notificaciones OK !");
			return notificacionDetalle;
				
		}).collect(Collectors.toList());
			
		
	}
	
	//Esto es para guardar la notificacion de una cita nueva
	public Notificacion saveNotificacionCitaNueva(CitaNuevaNotificacionResponde citaNueva) {
		
		try {
			
			Notificacion notificacion = new Notificacion();
			notificacion.setTitulo("Nueva Cita");
			notificacion.setMensaje("Se registró una nueva cita al sistema");
			notificacion.setFecha(LocalDate.now());
			notificacion.setFechaCita(citaNueva.getFechaCita());
			notificacion.setHoraCita(citaNueva.getHora());
			notificacion.setEstadoCita(citaNueva.getEstadoCita());
			notificacion.setNombreDoctor(citaNueva.getDoctorAsignado());
			notificacion.setPacienteId(citaNueva.getPacienteId());
			
			logger.info("Se registró una nueva notificación con una nueva cita con éxito !");
			return notificacionRepository.save(notificacion);
						
		} catch (Exception e) {
			logger.error("Error al registrar una notificación de una nueva cita: {}", e);
			throw new RuntimeException("Error al registrar una notificación de una cita nueva " + e.getMessage());
		}
	}
	
	
}
