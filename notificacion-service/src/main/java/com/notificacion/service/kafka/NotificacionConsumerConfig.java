package com.notificacion.service.kafka;

import java.util.HashMap;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import com.notificacion.service.dto.CitaNuevaNotificacionResponde;

@Configuration
public class NotificacionConsumerConfig {

	//private static Logger logger = LoggerFactory.getLogger(NotificacionConsumerConfig.class);
	
	@Autowired
	private KafkaProperties kafkaProperties;
	
	@Bean
	public ConsumerFactory<String, CitaNuevaNotificacionResponde> consumerFactory(){
		var configs = new HashMap<String, Object>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		
		JsonDeserializer<CitaNuevaNotificacionResponde> jsonDeserializer = new JsonDeserializer<>(CitaNuevaNotificacionResponde.class);
	    jsonDeserializer.addTrustedPackages("*"); // 🔹 Confía en cualquier paquete
				
	    return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), jsonDeserializer);
		
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, CitaNuevaNotificacionResponde> validMessageContainerFactory (ConsumerFactory<String, CitaNuevaNotificacionResponde> consumerFactory){
		var factory = new ConcurrentKafkaListenerContainerFactory<String, CitaNuevaNotificacionResponde>();
		factory.setConsumerFactory(consumerFactory);
		return factory;
	}
}
