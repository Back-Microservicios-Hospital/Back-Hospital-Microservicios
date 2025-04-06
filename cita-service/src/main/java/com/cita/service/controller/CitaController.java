package com.cita.service.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cita.service.dto.CitaDTO;
import com.cita.service.dto.CitaDetalleDTO;
import com.cita.service.dto.PacienteDTO;
import com.cita.service.entities.Cita;
import com.cita.service.entities.Estado;
import com.cita.service.service.CitaService;
import com.cita.service.service.EstadoService;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

	private static Logger logger = LoggerFactory.getLogger(CitaController.class);
	
	@Autowired
	private CitaService citaService;
	
	@Autowired
	private EstadoService estadoService;
	
	@GetMapping("/list/all")
	public ResponseEntity<?> getAllCitas(){
		
		try {
			
			List<CitaDetalleDTO> listCitas = citaService.findAllCitas();
			
			logger.info("Listado de Citas OK ! ");
			return new ResponseEntity<>(listCitas, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error al listar las citas {}", e);
			return new ResponseEntity<>(Map.of("error", "Error al listar todas las citas",
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/create")
	public ResponseEntity<?> createCita (@RequestBody CitaDTO citaDTO){
		
		try {
			
			Cita cita = new Cita();
			cita.setFecha(citaDTO.getFecha());
			cita.setDoctorId(citaDTO.getDoctorId());
			cita.setPacienteId(citaDTO.getPacienteId());
			
			Optional<Estado> estadoOptional = estadoService.findEstadoById(citaDTO.getEstadoId());
			
			if (estadoOptional.isEmpty()) {
				logger.error("Estado no encontrado con el id: {}", citaDTO.getEstadoId());
				throw new RuntimeException("Estado no encontrado con el id: " + citaDTO.getEstadoId());
			}
			
			Estado estado = estadoOptional.get();
			cita.setEstado(estado);
			
			citaService.saveCita(cita);
			
			logger.info("Cita creado con éxito: {}", cita);
			return new ResponseEntity<>(cita, HttpStatus.CREATED);		
			
		} catch (Exception e) {
			logger.error("Error al crear la cita {}", e );
			return new ResponseEntity<>(Map.of("error", "Error al crear la cita", 
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Buscar paciente por el dni
	@GetMapping("/find/paciente/dni/{dni}")
	public ResponseEntity<?> getPacienteByDni (@PathVariable String dni){
		
		try {
			
			PacienteDTO paciente = citaService.findPacienteByDni(dni);
			
			if (dni == null) {
				logger.error("DNI incorrecto: {}", dni);
				throw new RuntimeException("Escribir un dni válido");
			}
			if (paciente == null) {
				logger.error("Paciente no encontrado con el dni: {}", dni);
				throw new RuntimeException("Paciente no encontrado con el dni: " + dni);
			}
			
			logger.info("Paciente encontrado con el dni: {}", paciente);
			return new ResponseEntity<>(paciente, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error al buscar al paciente por el dni desde cita-service{} ", e );
			return new ResponseEntity<>(Map.of("error", "Error al buscar al paciente por el dni desde cita-service", 
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
