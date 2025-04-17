package com.notificacion.service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notificacion.service.coleccion.Notificacion;
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
	
	
}
