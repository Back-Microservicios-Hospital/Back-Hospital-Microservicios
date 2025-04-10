package com.historial.service.controller;

import java.util.List;
import java.util.Map;

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

import com.historial.service.coleccion.HistorialMedico;
import com.historial.service.dto.HistorialMedicoDTO;
import com.historial.service.dto.HistorialMedicoDetalleDTO;
import com.historial.service.service.HistorialMedicoService;

@RestController
@RequestMapping("/api/historial")
public class HistorialMedicoController {

	private static Logger logger = LoggerFactory.getLogger(HistorialMedicoController.class);
	
	@Autowired
	private HistorialMedicoService historialMedicoService;
	
	@GetMapping("/list")
	private ResponseEntity<?> getAllHistorial(){
		
		try {
			
			List<HistorialMedicoDetalleDTO> listHistorial = historialMedicoService.findAllHistoriales();
			
			logger.info("Listado de Historiales OK !");
			return new ResponseEntity<>(listHistorial, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error, al listar los historiales médicos {}", e);
			return new ResponseEntity<>(Map.of("error", "Error al listar los historiales médico",
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Buscar historial por DNI del paciente
	@GetMapping("/find/paciente/dni/{dni}")
	private ResponseEntity<?> getHistorialByPacienteDni (@PathVariable String dni){
		
		try {
			
			List<HistorialMedicoDetalleDTO> listHistorial = historialMedicoService.getHistorialByPacienteDni(dni);
			
			if (listHistorial.isEmpty()) {
				logger.error("Error, no se encontro historial con el DNI ingresado: {}", dni);
				throw new RuntimeException("Error, no se obtuvo resultados con el DNI ingresado: " + dni);
				
			}
			
			logger.info("Historial Controller: ");
			logger.info("El paciente con el DNI: {}, tiene los historiales: {}", dni, listHistorial);
			return new ResponseEntity<>(listHistorial, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error, al obtener historial con el DNI del paciente: {}", dni, e);
			return new ResponseEntity<>(Map.of("error", "Error al obtener historial con el DNI del paciente: " + dni,
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/create")
	private ResponseEntity<?> createHistorial (@RequestBody HistorialMedicoDTO historialMedicoDTO){
		
		try {
			
			HistorialMedico historial = new HistorialMedico();
			historial.setDiagnostico(historialMedicoDTO.getDiagnostico());
			historial.setReceta(historialMedicoDTO.getReceta());
			historial.setDoctorId(historialMedicoDTO.getDoctorId());
			historial.setPacienteId(historialMedicoDTO.getPacienteId());
			
			historialMedicoService.saveHistorial(historial);
			
			logger.info("Historial creado: {}", historial);
			return new ResponseEntity<>(historial, HttpStatus.CREATED);
			
		} catch (Exception e) {
			logger.error("Error, al crear el historial médico: {}", e);
			return new ResponseEntity<>(Map.of("error", "Error, al crear el historial médico",
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
