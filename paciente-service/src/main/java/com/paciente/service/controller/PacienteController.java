package com.paciente.service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paciente.service.dto.PacienteDTO;
import com.paciente.service.entities.Paciente;
import com.paciente.service.service.PacienteService;

@RestController
@RequestMapping("api-paciente")
public class PacienteController {

	private static Logger logger = LoggerFactory.getLogger(PacienteController.class);
	
	@Autowired
	private PacienteService pacienteService;
	

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
	public ResponseEntity<?> getAllPacientes(){
		
		try {
			
			List<Paciente> listPacientes = pacienteService.findAllPacientes();
			logger.info("Método getAllPacientes() OK!");
			return new ResponseEntity<>(listPacientes, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error en el método getAllPacientes()", e);
			return new ResponseEntity<>(Map.of("error", "Error al listar todos los pacientes",
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Listar con paginación
	@GetMapping("/list-pagination")
	public ResponseEntity<?> getPacientesByPagination(@RequestParam int page,
												      @RequestParam int size){
		
		try {
			
			Page<Paciente> pacientesPagination = pacienteService.getPacienteByPage(page, size);
			logger.info("Método getPacientesByPagination() OK !");
			return new ResponseEntity<>(pacientesPagination, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error en el método getPacientesByPagination() ", e);
			return new ResponseEntity<>(Map.of("error", "Error al obtener los pacientes por paginación",
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Buscar paciente por id
	@GetMapping("/find/{id}")
	public ResponseEntity<?> findPacienteById(@PathVariable Long id){
		
		try {
			
			Optional<Paciente> pacienteOptional = pacienteService.findPacienteById(id);
			
			if (pacienteOptional.isEmpty()) {
				logger.error("Error, paciente no encontrado con el id: ", id);
				throw new RuntimeException("Paciente no encontrado con el id : " + id);
			}
			
			logger.info("Paciente encontrado: {}", pacienteOptional);
			return new ResponseEntity<>(pacienteOptional, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error al encontrar el paciente por el id ", e);
			return new ResponseEntity<>(Map.of("error", "Error al encontrar el paciente por el id",
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createPaciente(@RequestBody PacienteDTO pacienteDTO){
		
		try {
			
			Paciente paciente = new Paciente();
			paciente.setNombre(pacienteDTO.getNombre());
			paciente.setApellido(pacienteDTO.getApellido());
			paciente.setDni(pacienteDTO.getDni());
			paciente.setTelefono(pacienteDTO.getTelefono());
			paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
			
			pacienteService.savePaciente(paciente);
			
			logger.info("Paciente creado con éxito: {}", paciente);
			return new ResponseEntity<>(paciente, HttpStatus.CREATED);			
			
		} catch (Exception e) {
			logger.error("Error al crear un nuevo paciente ", e);
			return new ResponseEntity<>(Map.of("error", "Error al crear un paciente nuevo",
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updatePaciente (@PathVariable Long id,
											 @RequestBody PacienteDTO pacienteDTO){
		
		try {
			
			Optional<Paciente> pacienteOptional = pacienteService.findPacienteById(id);
			
			if (pacienteOptional.isEmpty()) {
				logger.error("Error en el método updatePaciente(), paciente no encontrado con el id: {}", id);
				throw new RuntimeException("Paciente no encontrado con el id: " + id);
				
			}
			
			Paciente pacienteEncontrado = pacienteOptional.get();
			pacienteEncontrado.setNombre(pacienteDTO.getNombre());
			pacienteEncontrado.setApellido(pacienteDTO.getApellido());
			pacienteEncontrado.setDni(pacienteDTO.getDni());
			pacienteEncontrado.setTelefono(pacienteDTO.getTelefono());
			pacienteEncontrado.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
			
			pacienteService.savePaciente(pacienteEncontrado);
			
			logger.info("Paciente actualizado con éxito: {}", pacienteEncontrado);
			return new ResponseEntity<>(pacienteEncontrado, HttpStatus.OK);		
			
		} catch (Exception e) {
			logger.error("Error al actualizar el paciente", e);
			return new ResponseEntity<>(Map.of("error", "Error al actualizar al paciente",
									    "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deletePaciente (@PathVariable Long id){
		
		try {
			
			Optional<Paciente> pacienteOptional = pacienteService.findPacienteById(id);
			
			if (pacienteOptional.isEmpty()) {
				logger.error("Error al eliminar el paciente, no se encontro al paciente con el id: {}", id);
				throw new RuntimeException("Error, Paciente no encontrado con el id: " + id);
			}
			
			pacienteService.deletePaciente(id);
			
			logger.info("Paciente Eliminado con éxito");
			return new ResponseEntity<>("Paciente eliminado con éxito", HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error al eliminar el paciente", e);
			return new ResponseEntity<>(Map.of("error", "Error al eliminar el paciente", 
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
