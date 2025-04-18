package com.cita.service.kafka;

import java.util.HashMap;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaAdminConfig {

	@Autowired
	private KafkaProperties kafkaProperties;
	
	@Bean
	public KafkaAdmin kafkaAdmin() {
		var configs = new HashMap<String, Object>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
		return new KafkaAdmin(configs);
	}
	
	//Creando los topicos
	@Bean
	public KafkaAdmin.NewTopics topic(){
		return new KafkaAdmin.NewTopics(
				TopicBuilder.name("cita-nueva")
				.partitions(2)
				.replicas(1)
				.build(),
				TopicBuilder.name("cita-actualizada")
				.partitions(2)
				.replicas(1)
				.build()
				);				
	}
}
