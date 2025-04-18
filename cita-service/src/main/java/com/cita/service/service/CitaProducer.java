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
	
	@Autowired
	private KafkaTemplate<String, CitaNuevaNotificacionRequest> kafkaTemplateCitaEstadoActualizado;
	
	private static final String TOPIC_CITA_NUEVA = "cita-nueva";
	private static final String TOPIC_CITA_ESTADO_UPDATE = "cita-actualizada";
	
	//Esto es para notificar que se registro una nueva cita
	public void enviarNotificacionNuevaCita(CitaNuevaNotificacionRequest eventoCitaNueva) {
		kafkaTemplateCitaNueva.send(TOPIC_CITA_NUEVA, eventoCitaNueva).whenComplete((result, ex) -> {
			
			if (ex != null) {
				logger.error("Error al enviar la notificación: {}", ex.getMessage());
				return;
			}
			logger.info("-------------- Notificación Envio Kafka Logs -------------------");
			logger.info("Notificación de una cita nueva, enviada con éxito: {}", result.getProducerRecord().value());
			logger.info("CITA NUEVA ENVIADA A notificación-service con la siguiente información: ");
			logger.info("Cita notificada: {}", eventoCitaNueva);
			logger.info("Partición: {}, Offset: {}", result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
		});
	}
	
	//Esto es para notificar que se modifico una cita (el estado principalmente)
	public void enviarNotificacionCitaEstadoActualizado(CitaNuevaNotificacionRequest eventoCitaEstado) {
		kafkaTemplateCitaEstadoActualizado.send(TOPIC_CITA_ESTADO_UPDATE, eventoCitaEstado).whenComplete((result, ex) -> {
			
			if (ex != null) {
				logger.error("Error al enviar la notificación: {}", ex.getMessage());
				return;
			}
			logger.info("-------------- Notificación Envio Kafka Logs -------------------");
			logger.info("Notificación del estado de una cita actualizada, enviada con éxito: {}", result.getProducerRecord().value());
			logger.info("Estado de la cita actualizada y enviado a notificacion-service");
			logger.info("Estado actualizado: {}", eventoCitaEstado.getEstadoCita());
			logger.info("Cita actualizada notificación: {}", eventoCitaEstado);
			logger.info("Partición: {}, Offset: {}", result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
		});
	}
}
