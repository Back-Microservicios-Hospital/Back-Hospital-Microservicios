package com.doctor.service.graphql;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.doctor.service.entities.Doctor;
import com.doctor.service.service.DoctorService;

@Controller
public class GraphQLController {

	private static Logger logger = LoggerFactory.getLogger(GraphQLController.class);
	
	@Autowired
	private DoctorService doctorService;
	
	@QueryMapping(name = "findDoctores")
	public List<Doctor> getAllDoctores(){
		
		try {
			
			List<Doctor> listDoctor = doctorService.findAllDoctors();
			
			logger.info("Listado de todos los Doctores OK !" );
			return listDoctor;
			
		} catch (Exception e) {
			logger.error("Error al listar los doctores con GraphQL: {}", e);
			throw new RuntimeException("Error al listar los doctores con GraphQL " + e.getMessage());
		}
	}
	
	//Buscar doctor por apellido
	@QueryMapping(name = "findDoctorByApellido")
	public List<Doctor> getDoctorByApellido(@Argument(name = "apellido") String apellido){
		
		try {
			
			List<Doctor> listDoctor = doctorService.findDoctorByApellido(apellido);
			
			if (listDoctor.isEmpty()) {
				logger.error("Error, doctor no encontrado con el apellido: {}", apellido);
				throw new RuntimeException("Error, no se obtuvo resultado de búsqueda de doctor con el apellido: " + apellido);
			}
			
			logger.info("Doctor encontrado con el apellido ingresado: {}", listDoctor);
			return listDoctor;
			
		} catch (Exception e) {
			logger.error("Error al encontrar doctor por el apellido con GraphQL: {}", e);
			throw new RuntimeException("Error al encontrar doctor por el apellido con GraphQL " + e.getMessage());
		}
	}
}
