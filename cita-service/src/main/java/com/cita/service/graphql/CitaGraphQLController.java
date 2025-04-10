package com.cita.service.graphql;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	
	//Buscar todas las citas
	@QueryMapping(name = "findCitas")
	public List<CitaDetalleDTO> getAllCitas(){
		
		try {
			
			List<CitaDetalleDTO> listCitas = citaService.findAllCitas();
			
			logger.info("Búsqueda con GraphQL");
			logger.info("Búsqueda de todas las citas OK !");
			return listCitas;
			
		} catch (Exception e) {
			logger.error("Error, no se logro obtener todas las citas con GraphQL {}", e);
			throw new RuntimeException("Error, al obtener todas las citas con GraphQL " + e.getMessage());
		}
	}
	
	
	//Buscar cita por el dni del paciente
	@QueryMapping(name = "findCitaByPacienteDni")
	public List<CitaDetalleDTO> getCitaByPacienteDni(@Argument(name = "dni") String dni){
		
		try {
			
			List<CitaDetalleDTO> listCitas = citaService.findCitaByPacienteDni(dni);
			
			if (listCitas.isEmpty()) {
				logger.error("No se encontro cita con el dni: {}", dni);
				throw new RuntimeException("Error, no se encontro cita con el dni ingresado: " + dni);
			}
			
			logger.info("Búsqueda con GraphQL");
			logger.info("Se encontró la cita con el DNI ingresado con GraphQL: {}", listCitas);
			return listCitas;
			
		} catch (Exception e) {
			logger.error("Error al buscar cita por el DNI del paciente con GraphQL{}", e);
			throw new RuntimeException("Error, al buscar cita por el DNI del paciente con GraphQL" + e.getMessage());
		}
	}
	
	//Buscar cita por fecha
	@QueryMapping(name = "findCitaByFecha")
	public List<CitaDetalleDTO> getCitaByFecha(@Argument (name = "fecha") String fecha){
		
		try {
			
			//Con esto formateo la fecha de String a LocalDate
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(fecha, formatter);
			
			List<CitaDetalleDTO> listCitas = citaService.findCitaByFecha(localDate);
			
			if (listCitas.isEmpty()) {
				logger.error("Error, no se encontro citas con la fecha ingresada: {}", fecha);
				throw new RuntimeException("Error, no hay citas con la fecha ingresada: " + fecha);
			}
			
			logger.info("Búsqueda con GraphQL");
			logger.info("Cita(s) encontrada para la fecha ingresada: {}", listCitas);
			return listCitas;
			
		} catch (Exception e) {
			logger.error("Error, no se pudo buscar citas por la fecha con GraphQL {}", e);
			throw new RuntimeException("Error, no se pudo buscar citas por la fecha con GraphQL " + e.getMessage());
		}
	}
	
	//Buscar cita por apellido del doctor
	@QueryMapping(name = "findCitaByDoctorApellido")
	public List<CitaDetalleDTO> getCitaByDoctorApellido (@Argument(name = "apellido") String apellido){
		
		try {
			
			List<CitaDetalleDTO> listCitas = citaService.findCitaByDoctorApellido(apellido);
			
			if (listCitas.isEmpty()) {
				logger.error("El Doctor: {} no tiene asignado citas", apellido);
				throw new RuntimeException("Error, el doctor no tiene citas asignadas ");
			}
			
			logger.info("Búsqueda con GraphQL");
			logger.info("El Doctor: {} tiene las citas: {}", apellido, listCitas);
			return listCitas;
			
		} catch (Exception e) {
			logger.error("Error, no se pudo buscar citas por el apellido del doctor con GraphQL {}", e);
			throw new RuntimeException("Error, no se pudo buscar citas por el apellido del doctor con GraphQL " + e.getMessage());
		}
	}
}
