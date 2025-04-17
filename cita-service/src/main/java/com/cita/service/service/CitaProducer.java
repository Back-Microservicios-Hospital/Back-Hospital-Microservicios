package com.cita.service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.cita.service.dto.CitaNuevaNotificacionRequest;

@Service
public class CitaProducer {

	private static Logger logger = LoggerFactory.getLogger(CitaProducer.class);
	
	@Autowired
	private KafkaTemplate<String, CitaNuevaNotificacionRequest> kafkaTemplateCitaNueva;
	
	private static final String TOPIC_CITA_NUEVA = "cita-nueva";
	
	//Esto es para notificar que se registro una nueva cita
	public void enviarNotificacionNuevaCita(CitaNuevaNotificacionRequest eventoCitaNueva) {
		kafkaTemplateCitaNueva.send(TOPIC_CITA_NUEVA, eventoCitaNueva).whenComplete((result, ex) -> {
			
			if (ex != null) {
				logger.error("Error al enviar la notificación: {}", ex.getMessage());
				return;
			}
			
			logger.info("Notificación enviada con éxito enviarNotificacionNuevaCita: {}", result.getProducerRecord().value());
			logger.info("CITA NUEVA ENVIADA A notificación-service con la siguiente información: ");
			logger.info("Cita notificada: {}", eventoCitaNueva);
			logger.info("Partición {}, Offset {}", result.getRecordMetadata().partition());
		});
	}
}
