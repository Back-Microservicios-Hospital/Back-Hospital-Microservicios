package com.cita.service.graphql;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.cita.service.dto.CitaDetalleDTO;
import com.cita.service.service.CitaService;

@Controller
public class CitaGraphQLController {

	private static Logger logger = LoggerFactory.getLogger(CitaGraphQLController.class);
	
	@Autowired
	private CitaService citaService;
	
	@QueryMapping(name = "findCitaByPacienteDni")
	public List<CitaDetalleDTO> getCitaByPacienteDni(@Argument(name = "dni") String dni){
		
		try {
			
			List<CitaDetalleDTO> listCitas = citaService.findCitaByPacienteDni(dni);
			
			if (listCitas.isEmpty()) {
				logger.error("No se encontro cita con el dni: {}", dni);
				throw new RuntimeException("Error, no se encontro cita con el dni ingresado: " + dni);
			}
			
			logger.info("Se encontró la cita con el DNI ingresado con GraphQL: {}", listCitas);
			return listCitas;
			
		} catch (Exception e) {
			logger.error("Error al buscar cita por el DNI del paciente con GraphQL{}", e);
			throw new RuntimeException("Error, al buscar cita por el DNI del paciente con GraphQL" + e.getMessage());
		}
	}
}
