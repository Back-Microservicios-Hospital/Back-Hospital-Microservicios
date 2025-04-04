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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cita.service.dto.CitaDTO;
import com.cita.service.dto.CitaDetalleDTO;
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
			logger.error("Error al crear la cita ", e );
			return new ResponseEntity<>(Map.of("error", "Error al crear la cita", 
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
