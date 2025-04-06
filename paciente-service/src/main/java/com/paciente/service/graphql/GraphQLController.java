package com.paciente.service.graphql;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
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
	
	//Buscar Paciente por apellido
	@QueryMapping(name = "findPacienteByApellido")
	public List<Paciente> getPacienteByApellido(@Argument(name = "apellido") String apellido){
		
		try {
			
			List<Paciente> listPaciente = pacienteService.findPacienteByApelllido(apellido);
			
			if (listPaciente.isEmpty()) {
				logger.error("Error, no hay pacientes con el apellido: {}", apellido);
				throw new RuntimeException("Error, no hay pacientes con el apellido: " + apellido);
			}
			
			logger.info("Paciente(s) encontrado: {}", listPaciente);
			return listPaciente;
			
		} catch (Exception e) {
			logger.error("Error al obtener paciente por apellido(s) con GraphQL {}", e);
			throw new RuntimeException("Error al obtener paciente(s) por apellido con GraphQL"+ e.getMessage());
		}
	}
	
	//Buscar paciente por dni
	@QueryMapping(name = "findPacienteByDni")
	public Paciente getPacienteByDni (@Argument(name = "dni") String dni) {
		
		try {
			
			Paciente paciente = pacienteService.findPacienteByDni(dni);
			
			if (paciente == null) {
				logger.error("Error, paciente no encontrado con el dni: {}", dni);
				throw new RuntimeException("Error, paciente no encontrado con el dni: "+ dni);
			}
			
			logger.info("Paciente encontrado con el dni: {}", paciente);
			return paciente;
			
		} catch (Exception e) {
			logger.error("Error al obtener paciente por el dni con GraphQL {}", e);
			throw new RuntimeException("Error al obtener paciente por el dni con GraphQL"+ e.getMessage());
		}
	}
		
}
