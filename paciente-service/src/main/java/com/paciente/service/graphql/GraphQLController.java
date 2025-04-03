package com.paciente.service.graphql;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.paciente.service.entities.Paciente;
import com.paciente.service.service.PacienteService;

@Controller
public class GraphQLController {
	
	private static Logger logger = LoggerFactory.getLogger(GraphQLController.class);
	
	@Autowired
	private PacienteService pacienteService;
	
	@QueryMapping(name = "findPacientes")
	public List<Paciente> getAllPacientes(){
		
		try {
			
			List<Paciente> listPacientes = pacienteService.findAllPacientes();
			
			logger.info("Lista de Pacientes OK con GraphQL !!");
			return listPacientes;
			
		} catch (Exception e) {
			logger.error("Error al listar todos los pacientes con GraphQL {}", e);
			throw new RuntimeException("Error al listar los pacientes con GraphQL" + e.getMessage());
		}
	}
	
	
	
}
