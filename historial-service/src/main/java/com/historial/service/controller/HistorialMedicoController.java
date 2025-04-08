package com.historial.service.controller;

import java.util.List;
import java.util.Map;

import org.apache.http.protocol.HTTP;
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
	
	
	@PostMapping("/create")
	private ResponseEntity<?> createHistorial (@RequestBody HistorialMedicoDTO historialMedicoDTO){
		
		try {
			
			HistorialMedico historial = new HistorialMedico();
			historial.setDiagnostico(historialMedicoDTO.getDiagnostico());
			historial.setReceta(historialMedicoDTO.getReceta());
			historial.setDoctorId(historialMedicoDTO.getDoctorId());
			historial.setPaciendId(historialMedicoDTO.getPaciendId());
			
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
