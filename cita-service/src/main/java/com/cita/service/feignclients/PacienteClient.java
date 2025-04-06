package com.cita.service.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cita.service.dto.PacienteDTO;

//@FeignClient(name = "paciente-service") //Con Eureka
@FeignClient(name = "paciente-service", url = "http://localhost:9091", path = "/api/pacientes") //Sin Eureka
public interface PacienteClient {

	@GetMapping("/find/{id}")
	PacienteDTO getPacienteById(@PathVariable Long id);
	
	@GetMapping("/find/dni/{dni}")
	PacienteDTO getPacienteByDni(@PathVariable String dni);
}
