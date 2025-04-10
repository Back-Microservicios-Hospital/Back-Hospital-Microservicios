package com.doctor.service.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doctor.service.dto.DoctorDTO;
import com.doctor.service.entities.Doctor;
import com.doctor.service.entities.Especialidad;
import com.doctor.service.service.DoctorService;
import com.doctor.service.service.EspecialidadService;

@RestController
@RequestMapping("/api/doctores")
public class DoctorController {

	private static Logger logger = LoggerFactory.getLogger(DoctorController.class);

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private EspecialidadService especialidadService;

	@GetMapping("/list/all")
	public ResponseEntity<?> listAllDoctors() {

		try {

			List<Doctor> listDoctors = doctorService.findAllDoctors();

			logger.info("Listado de Doctores OK !");
			return new ResponseEntity<>(listDoctors, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error al listar todos los doctores {}", e);
			return new ResponseEntity<>(
					Map.of("error", "Error al listar todos los doctores", "detalle", e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Listar doctores con paginacion
	@GetMapping("/list/pagination")
	public ResponseEntity<?> listDoctorsByPagination(@RequestParam int page, @RequestParam int size) {

		try {

			Page<Doctor> listPagination = doctorService.findDoctorByPagination(page, size);

			logger.info("Listado de doctores con paginación OK !");
			return new ResponseEntity<>(listPagination, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error al listar todos los doctores con paginación {}", e);
			return new ResponseEntity<>(
					Map.of("error", "Error al listar todos los doctores con paginación", "detalle", e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// Buscar un doctor por su id
	@GetMapping("/find/{id}")
	public ResponseEntity<?> findDoctorById(@PathVariable Long id) {

		try {

			Optional<Doctor> doctorOptional = doctorService.findDoctorById(id);

			if (doctorOptional.isEmpty()) {
				logger.error("Error, doctor no encontrado con el id: {}", id);
				throw new RuntimeException("Error, doctor no encontrado con el id: " + id);
			}

			logger.info("Doctor encontrado: {}", doctorOptional);
			return new ResponseEntity<>(doctorOptional, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error, al encontrado el Doctor {}", e);
			return new ResponseEntity<>(Map.of("error", "Error al encontrar el doctor", "detalle", e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Buscar doctor por apellido
	@GetMapping("/find/apellido/{apellido}")
	public ResponseEntity<?> getDoctorByApellido(@PathVariable String apellido) {

		try {

			List<Doctor> listPaciente = doctorService.findDoctorByApellido(apellido);

			if (listPaciente.isEmpty()) {
				logger.error("Error, no se encontro doctor con el apellido: {}", apellido);
				throw new RuntimeException("Error, no se obtuvo al doctor con el apellido: " + apellido);
			}

			logger.info("Doctor encontrado con el apellido ingresado: {}", listPaciente);
			return new ResponseEntity<>(listPaciente, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error, al encontrado el Doctor por el apellido {}", e);
			return new ResponseEntity<>(
					Map.of("error", "Error al encontrar el doctor por el apellido", "detalle", e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PostMapping("/create")
	public ResponseEntity<?> createDoctor(@RequestBody DoctorDTO doctorDTO) {

		try {

			Doctor doctor = new Doctor();
			doctor.setNombre(doctorDTO.getNombre());
			doctor.setApellido(doctorDTO.getApellido());
			doctor.setTelefono(doctorDTO.getTelefono());
			doctor.setEmail(doctorDTO.getEmail());

			Optional<Especialidad> especialidadOptional = especialidadService
					.findEspecialidadById(doctorDTO.getEspecialidadId());

			if (especialidadOptional.isEmpty()) {
				logger.error("Error, especialidad no encontrada con el id: ", doctorDTO.getEspecialidadId());
				throw new RuntimeException(
						"Error, Especialidad no encontrada con el id : " + doctorDTO.getEspecialidadId());
			}

			Especialidad especialidad = especialidadOptional.get();

			doctor.setEspecialidad(especialidad);

			doctorService.saveDoctor(doctor);

			logger.info("Doctor creado: {}", doctor);
			return new ResponseEntity<>(doctor, HttpStatus.CREATED);

		} catch (Exception e) {
			logger.error("Error al crear el doctor {}", e);
			return new ResponseEntity<>(Map.of("error", "Error al crear el doctor", "detalle", e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateDoctor(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO) {

		try {

			Optional<Doctor> doctorOptional = doctorService.findDoctorById(id);

			if (doctorOptional.isEmpty()) {
				logger.error("Error, doctor no encontrado con el id: {}", id);
				throw new RuntimeException("Error, doctor no encontrado con el id: " + id);
			}

			Doctor doctor = doctorOptional.get();
			doctor.setNombre(doctorDTO.getNombre());
			doctor.setApellido(doctorDTO.getApellido());
			doctor.setTelefono(doctorDTO.getTelefono());
			doctor.setEmail(doctorDTO.getEmail());

			Optional<Especialidad> especialidadOptional = especialidadService
					.findEspecialidadById(doctorDTO.getEspecialidadId());

			if (especialidadOptional.isEmpty()) {
				logger.error("Error, especialidad no encontrada con el id: ", doctorDTO.getEspecialidadId());
				throw new RuntimeException(
						"Error, Especialidad no encontrada con el id : " + doctorDTO.getEspecialidadId());
			}

			Especialidad especialidad = especialidadOptional.get();

			doctor.setEspecialidad(especialidad);

			doctorService.saveDoctor(doctor);

			logger.info("Doctor actualizado con éxito: {}", doctor);
			return new ResponseEntity<>(doctor, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error al actualizar el doctor {}", e);
			return new ResponseEntity<>(Map.of("error", "Error al actualizar el doctor", "detalle", e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {

		try {

			Optional<Doctor> doctorOptional = doctorService.findDoctorById(id);

			if (doctorOptional.isEmpty()) {
				logger.error("Error, doctor no encontrado con el id: {}", id);
				throw new RuntimeException("Error, doctor no encontrado con el id: " + id);
			}

			doctorService.deleteDoctor(id);

			logger.info("Doctor eliminado con éxito !");
			return new ResponseEntity<>(Map.of("mensaje", "Doctor eliminado con éxito"), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error al eliminar el doctor {}", e);
			return new ResponseEntity<>(Map.of("error", "Error al eliminar el doctor", "detalle", e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
