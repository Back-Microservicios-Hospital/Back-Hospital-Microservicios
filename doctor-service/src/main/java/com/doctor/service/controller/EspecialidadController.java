package com.doctor.service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.doctor.service.dto.EspecialidadDTO;
import com.doctor.service.entities.Especialidad;
import com.doctor.service.service.EspecialidadService;

@RestController
@RequestMapping("/api-especialidad")
public class EspecialidadController {

	private static Logger logger = LoggerFactory.getLogger(EspecialidadController.class);
	
	@Autowired
	private EspecialidadService especialidadService;
	
	@Value("${message}")
	private String message;

	@Value("${global-message}")
	private String globalMessage;

	@RequestMapping(method = RequestMethod.GET)
	public Map<String, String> message() {
		Map<String, String> response = new HashMap<>();
		response.put("message", message);
		response.put("global-message", globalMessage);
		return response;
	}
	
	
	
	@GetMapping("/list-all")
	public ResponseEntity<?> findAllEspecialidad(){
		
		try {
			
			List<Especialidad> listEspecialidad = especialidadService.findAllEspecialidad();
			
			logger.info("Listar todas las especialidades OK! ");
			return new ResponseEntity<>(listEspecialidad, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error al listar las especialidades ", e);
			return new ResponseEntity<>(Map.of("error", "Error al listar las especialidades",
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Buscar especialidad por id
	@GetMapping("/find/{id}")
	public ResponseEntity<?> findEspecialidadById(@PathVariable Long id){
		
		try {
			
			Optional<Especialidad> especialidadOptional = especialidadService.findEspecialidadById(id);
			
			if (especialidadOptional.isEmpty()) {
				
				logger.error("Especialidad no encontrada con el id: {}", id);
				throw new RuntimeException("Especialidad no encontrada con el id: " + id);
			}
			
			logger.info("Especialidad encontrada: {}", especialidadOptional);
			return new ResponseEntity<>(especialidadOptional, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error al encontrar la especialidad por el id ", e);
			return new ResponseEntity<>(Map.of("error", "Error al encontrar la especialidad por el id",
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createEspecialidad(@RequestBody EspecialidadDTO especialidadDTO){
		
		try {
			
			Especialidad especialidad = new Especialidad();
			especialidad.setNombre(especialidadDTO.getNombre());
			
			especialidadService.saveEspecialidad(especialidad);
			
			logger.info("Especialidad creada: {}", especialidad);
			return new ResponseEntity<>(especialidad, HttpStatus.CREATED);
			
		} catch (Exception e) {
			logger.error("Error al crear la especialidad !", e);
			return new ResponseEntity<>(Map.of("error", "Error al crear la especialidad",
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateEspecialidad(@PathVariable Long id,
												@RequestBody EspecialidadDTO especialidadDTO){
		
		try {
			
			Optional<Especialidad> especialidadOptional = especialidadService.findEspecialidadById(id);
			
			if (especialidadOptional.isEmpty()) {
				logger.error("No se encontro la especialidad con el id: {}", id);
				throw new RuntimeException("Especialidad no encontrada con el id: " + id);
			}
			
			Especialidad especialidad = especialidadOptional.get();
			especialidad.setNombre(especialidadDTO.getNombre());
			
			especialidadService.saveEspecialidad(especialidad);
			
			logger.info("Especialidad actualizada con éxito: {}", especialidad);
			return new ResponseEntity<>(especialidad, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error al actualizar la especialidad ", e);
			return new ResponseEntity<>(Map.of("error", "Error al actualizar la especialidad",
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteEspecialidad(@PathVariable Long id){
		
		try {
			
			Optional<Especialidad> especialidadOptional = especialidadService.findEspecialidadById(id);
			
			if (especialidadOptional.isEmpty()) {
				logger.error("Especialidad no encontrada con el id: {}", id);
				throw new RuntimeException("Especialidad no encontrada con el id: " + id);
			}
			
			especialidadService.deleteEspecialidadById(id);
			
			logger.info("Especialidad eliminada con éxito");
			return new ResponseEntity<>("Especialidad eliminada !", HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error al eliminar la especialidad " , e);
			return new ResponseEntity<>(Map.of("error", "Error al eliminar la especialidad",
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
