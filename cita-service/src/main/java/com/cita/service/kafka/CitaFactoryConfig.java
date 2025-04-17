package com.cita.service.kafka;

import java.util.HashMap;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.cita.service.dto.CitaNuevaNotificacionRequest;

@Configuration
public class CitaFactoryConfig {

	@Autowired
	private KafkaProperties kafkaProperties;
	
	//Este mensaje es para notificar de una nueva cita
	public ProducerFactory<String, CitaNuevaNotificacionRequest> notificacionProducerFactory(){
		
		var configs = new HashMap<String, Object>();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		configs.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false); // 🔹 No incluir info del paquete
		return new DefaultKafkaProducerFactory<>(configs);
	}
	
	@Bean
	public KafkaTemplate<String, CitaNuevaNotificacionRequest> notificacionKafkaTemplate(){
		return new KafkaTemplate<>(notificacionProducerFactory());
	}
}
