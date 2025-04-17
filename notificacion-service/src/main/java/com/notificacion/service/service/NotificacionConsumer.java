package com.notificacion.service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.notificacion.service.dto.CitaNuevaNotificacionResponde;

@Service
public class NotificacionConsumer {

	private static Logger logger = LoggerFactory.getLogger(NotificacionConsumer.class);
	
	@Autowired
	private NotificacionService notificacionService;
	
	@KafkaListener(topics = "cita-nueva", groupId = "grupo-cita", containerFactory = "validMessageContainerFactory")
	public void receptorNotificacionCitaNueva(CitaNuevaNotificacionResponde eventoCitaNueva) {
		logger.info("----------- Servicio de Notificaciones ----------");
		logger.info("Evento: {}", eventoCitaNueva);
		logger.info("Se obtuvo el mensaje para receptorNotificacionCitaNueva para registrar una notificación con una nueva cita");
		logger.info("Datos de la cita nueva: {}", eventoCitaNueva);
		notificacionService.saveNotificacionCitaNueva(eventoCitaNueva);
	}
}
