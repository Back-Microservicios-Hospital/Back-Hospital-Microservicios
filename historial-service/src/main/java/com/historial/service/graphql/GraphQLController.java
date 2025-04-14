package com.historial.service.graphql;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.historial.service.dto.HistorialMedicoDetalleDTO;
import com.historial.service.service.HistorialMedicoService;

@Controller
public class GraphQLController {

	private static Logger logger = LoggerFactory.getLogger(GraphQLController.class);
	
	@Autowired
	private HistorialMedicoService historialMedicoService;
	
	@QueryMapping(name = "findHistoriales")
	public List<HistorialMedicoDetalleDTO> getAllHistorialMedico (){
		
		try {
			
			List<HistorialMedicoDetalleDTO> listHistorial = historialMedicoService.findAllHistoriales();
			
			logger.info("Listado de Historiales Médico con GraphQL OK !");
			return listHistorial;
			
		} catch (Exception e) {
			logger.error("Error, al listar los historiales con GraphQL: {}", e);
			throw new RuntimeException("Error, al listar los historiales con GraphQL: " + e.getMessage());
		}
	}
}
