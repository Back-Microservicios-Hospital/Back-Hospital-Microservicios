package com.historial.service.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.historial.service.dto.DoctorDTO;

//@FeignClient(name = "paciente-service") //Con Eureka
@FeignClient(name = "doctor-service", url = "http://localhost:9092", path = "/api/doctores") //Sin Eureka
public interface DoctorClient {

	@GetMapping("/find/{id}")
	DoctorDTO getDoctorById(@PathVariable Long id);
}
