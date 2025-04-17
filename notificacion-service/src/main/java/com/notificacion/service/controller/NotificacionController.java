package com.notificacion.service.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notificacion.service.dto.NotificacionDetalleDTO;
import com.notificacion.service.service.NotificacionService;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

	private static Logger logger = LoggerFactory.getLogger(NotificacionController.class);
	
	@Autowired
	private NotificacionService notificacionService;
	
	@GetMapping("/find/all")
	public ResponseEntity<?> getAllNotificaciones (){
		
		try {
			
			List<NotificacionDetalleDTO> notificaciones = notificacionService.findAllNotificaciones();
			
			logger.info("Listado correcto de las notificaciones !");
			return new ResponseEntity<>(notificaciones, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error, al listar las notificaciones {}", e);
			return new ResponseEntity<>(Map.of("error", "Error al listar las notificaciones", 
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
