package com.cita.service.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cita.service.dto.EstadoDTO;
import com.cita.service.entities.Estado;
import com.cita.service.service.EstadoService;

@RestController
@RequestMapping("/api/estados")
public class EstadoController {

	private static Logger logger = LoggerFactory.getLogger(EstadoController.class);
	
	@Autowired
	private EstadoService estadoService;
	
	@GetMapping("/list/all")
	public ResponseEntity<?> getAllEstados(){
		
		try {
			
			List<Estado> listEstados = estadoService.findAllEstados();
			
			logger.info("Listado de todos los Estados OK !");
			return new ResponseEntity<>(listEstados, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error al listar los Estados {}", e);
			return new ResponseEntity<>(Map.of("error", "Error al listar los Estados",
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Buscar estado por id
	@GetMapping("/find/{id}")
	public ResponseEntity<?> getEstadoById(@PathVariable Long id){
		
		try {
			
			Optional<Estado> estadoOptional = estadoService.findEstadoById(id);
			
			if (estadoOptional.isEmpty()) {
				logger.error("Estado no encontrado con el id: {}", id);
				throw new RuntimeException("Estado no encontrado con el id: " + id);
			}
			
			logger.info("Estado encontrado: {}", estadoOptional);
			return new ResponseEntity<>(estadoOptional, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error al encontrar el Estado por el id {}", e);
			return new ResponseEntity<>(Map.of("error", "Error al encontrar el Estado por el id",
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createEstado (@RequestBody EstadoDTO estadoDTO){
		
		try {
			
			Estado estado = new Estado();
			estado.setNombre(estadoDTO.getNombre());
			
			estadoService.saveEstado(estado);
			
			logger.info("Estado creado con éxito: {}", estado);
			return new ResponseEntity<>(estado, HttpStatus.CREATED);
			
		} catch (Exception e) {
			logger.error("Error al crear el Estado {}", e);
			return new ResponseEntity<>(Map.of("error", "Error al crear el Estado",
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateEstado(@PathVariable Long id,
										  @RequestBody EstadoDTO estadoDTO){
		
		try {
			
			Optional<Estado> estadoOptional = estadoService.findEstadoById(id);
			
			if (estadoOptional.isEmpty()) {
				logger.error("Estado no encontrado con el id: {}", id);
				throw new RuntimeException("Estado no encontrado con el id: " + id);
			}
			
			Estado estado = estadoOptional.get();
			estado.setNombre(estadoDTO.getNombre());
			
			estadoService.saveEstado(estado);
			
			logger.info("Estado actualizado con éxito: {}", estado);
			return new ResponseEntity<>(estado, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error al actualizar el Estado {}", e);
			return new ResponseEntity<>(Map.of("error", "Error al actualizar el Estado",
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteEstado (@PathVariable Long id){
		
		try {
			
			Optional<Estado> estadoOptional = estadoService.findEstadoById(id);
			
			if (estadoOptional.isEmpty()) {
				logger.error("Estado no encontrado con el id: {}", id);
				throw new RuntimeException("Estado no encontrado con el id: " + id);
			}
			
			estadoService.deleteEstado(id);
			
			logger.info("Estado eliminado con éxito !");
			return new ResponseEntity<>(Map.of("mensaje", "Estado eliminado con éxito"), HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error al eliminar el Estado {}", e);
			return new ResponseEntity<>(Map.of("error", "Error al eliminar el Estado",
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
