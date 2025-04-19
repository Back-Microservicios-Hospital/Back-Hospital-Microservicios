package com.cita.service.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cita.service.dto.CitaDTO;
import com.cita.service.dto.CitaDetalleDTO;
import com.cita.service.entities.Cita;
import com.cita.service.entities.Estado;
import com.cita.service.service.CitaService;
import com.cita.service.service.EstadoService;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

	private static Logger logger = LoggerFactory.getLogger(CitaController.class);
	
	@Autowired
	private CitaService citaService;
	
	@Autowired
	private EstadoService estadoService;
	
	@GetMapping("/list/all")
	public ResponseEntity<?> getAllCitas(){
		
		try {
			
			List<CitaDetalleDTO> listCitas = citaService.findAllCitas();
			
			logger.info("Listado de Citas OK ! ");
			return new ResponseEntity<>(listCitas, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error al listar las citas {}", e);
			return new ResponseEntity<>(Map.of("error", "Error al listar todas las citas",
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Listar citas con paginacion
	@GetMapping("/find/pagination")
	public ResponseEntity<?> getCitasByPagination(@RequestParam int page,
												  @RequestParam int size){
		
		try {
			
			Page<CitaDetalleDTO> citaPaginadas = citaService.findCitasByPagination(page, size);
			logger.info("Listado de Citas con paginación OK !");
			return new ResponseEntity<>(citaPaginadas, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error al listar las citas con paginación: {}", e);
			return new ResponseEntity<>(Map.of("error", "Error al listar citas por paginación",
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	
	@PostMapping("/create")
	public ResponseEntity<?> createCita (@RequestBody CitaDTO citaDTO){
		
		try {
			
			Cita cita = new Cita();
			cita.setFecha(citaDTO.getFecha());
			cita.setHora(citaDTO.getHora());
			cita.setDoctorId(citaDTO.getDoctorId());
			cita.setPacienteId(citaDTO.getPacienteId());
			
			Optional<Estado> estadoOptional = estadoService.findEstadoById(citaDTO.getEstadoId());
			
			if (estadoOptional.isEmpty()) {
				logger.error("Estado no encontrado con el id: {}", citaDTO.getEstadoId());
				throw new RuntimeException("Estado no encontrado con el id: " + citaDTO.getEstadoId());
			}
			
			Estado estado = estadoOptional.get();
			cita.setEstado(estado);
			
			citaService.saveCita(cita);
			
			logger.info("Cita creado con éxito: {}", cita);
			return new ResponseEntity<>(cita, HttpStatus.CREATED);		
			
		} catch (Exception e) {
			logger.error("Error al crear la cita {}", e );
			return new ResponseEntity<>(Map.of("error", "Error al crear la cita", 
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Buscar cita con dni del paciente
	@GetMapping("/find/cita/dni/{dni}")
	public ResponseEntity<?> getCitaByPacienteDni (@PathVariable String dni){
		
		try {
			
			List<CitaDetalleDTO> listCitas = citaService.findCitaByPacienteDni(dni);
			
			if (listCitas.isEmpty()) {
				logger.error("No hay citas con el DNI del paciente: {}", dni);
				throw new RuntimeException("Error, no se encontro cita con el DNI del paciente: " + dni);
			}
			
			logger.info("Cita encontrado con el DNI ingresado: {}", listCitas);
			return new ResponseEntity<>(listCitas, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error al buscar citas por el DNI del paciente: {}",dni, e);
			return new ResponseEntity<>(Map.of("error", "Error al buscar citas con el DNI del paciente: " + dni,
					    					    "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Buscar cita por fecha
	@GetMapping("/find/fecha/{fecha}")
	public ResponseEntity<?> getCitaByFecha(@PathVariable String fecha){
		
		try {
			
			//Con esto formateo la fecha de String a LocalDate
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(fecha, formatter);
			
			List<CitaDetalleDTO> listCitas = citaService.findCitaByFecha(localDate);
			
			if (listCitas.isEmpty()) {
				logger.error("Error, no se encontro citas con la fecha: {}", fecha);
				throw new RuntimeException("No se encontró citas con la fecha: " + fecha);
			}
			
			logger.info("Log del Controller");
			logger.info("Citas encontradas por la fecha: {}", listCitas);
			return new ResponseEntity<>(listCitas, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error, al encontrar cita por la fecha desde el Controller : {}", e);
			return new ResponseEntity<>(Map.of("error", "Error, al encontrar cita con la fecha: " + fecha,
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Buscar cita por apellido del Doctor
	@GetMapping("/find/doctor/apellido/{apellido}")
	public ResponseEntity<?> getCitaByDoctorApellido(@PathVariable String apellido){
		
		try {
			
			List<CitaDetalleDTO> listCitas = citaService.findCitaByDoctorApellido(apellido);
			
			if (listCitas.isEmpty()) {
				logger.error("Doctor apellido: {} No tiene asignado citas", apellido);
				throw new RuntimeException("Error, no se encontro ninguna cita con el apellido del doctor: " + apellido);
			}
			
			logger.info("Doctor apellido: {} , tiene asignados las citas: {}", apellido, listCitas);
			return new ResponseEntity<>(listCitas, HttpStatus.OK);
						
		} catch (Exception e) {
			logger.error("Error al buscar citas por el apellido del docente {}", e);
			return new ResponseEntity<>(Map.of("error", "Error al buscar citas con el apellido del doctor: " + apellido,
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//Actualizar solo el estado de una cita
	@PatchMapping("/{id}/estado")
	public ResponseEntity<?> updateCitaEstado (@PathVariable Long id,
											   @RequestBody Estado estado){
		
		try {
			
			Optional<Cita> citaOptional = citaService.findCitaById(id);
			
			if (citaOptional.isEmpty()) {
				logger.error("Error, cita no encontrada con el id: {}", id);
				throw new RuntimeException("Cita no encontrada con el id: " + id);
			}
			
			Optional<Estado> estadoOptional = estadoService.findEstadoById(estado.getId());
			
			if (estadoOptional.isEmpty()) {
				logger.error("Error, estado no encontrado con el id: {}", estado.getId());
				throw new RuntimeException("Error, estado no encontrado con el id: " + estado.getId());
			}
			
			Estado nuevoEstado = estadoOptional.get();
			Cita cita = citaOptional.get();
			
			cita.setEstado(nuevoEstado);
			
			//citaService.saveCita(cita);
			
			//Esto es para kafka producer y enviar la notificacion del estado actualizado
			//Ademas actualiza la cita y su estado
			citaService.updateCitaEstado(cita);
			
			logger.info("Cita actualizada con el nuevo estado: {}", cita);
			return new ResponseEntity<>(cita, HttpStatus.OK);
			
			
		} catch (Exception e) {
			logger.error("Error al intentar acualizar el estado de una cita {}", e);
			return new ResponseEntity<>(Map.of("error", "Error al intentar acualizar el estado de una cita",
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*
	//Buscar paciente por el dni
	@GetMapping("/find/paciente/dni/{dni}")
	public ResponseEntity<?> getPacienteByDni (@PathVariable String dni){
		
		try {
			
			PacienteDTO paciente = citaService.findPacienteByDni(dni);
			
			if (dni == null) {
				logger.error("DNI incorrecto: {}", dni);
				throw new RuntimeException("Escribir un dni válido");
			}
			if (paciente == null) {
				logger.error("Paciente no encontrado con el dni: {}", dni);
				throw new RuntimeException("Paciente no encontrado con el dni: " + dni);
			}
			
			logger.info("Paciente encontrado con el dni: {}", paciente);
			return new ResponseEntity<>(paciente, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error al buscar al paciente por el dni desde cita-service{} ", e );
			return new ResponseEntity<>(Map.of("error", "Error al buscar al paciente por el dni desde cita-service", 
											   "detalle", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	*/
}
