package com.notificacion.service.coleccion;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "notificaciones")
public class Notificacion {

	@Id
	private String id;
	private String titulo;
	private String mensaje;
	private LocalDate fecha;
	
	@Field(name = "hora_cita")
	private String horaCita;
	
	@Field(name = "fecha_cita")
	private LocalDate fechaCita;
	
	@Field(name = "estado_cita")
	private String estadoCita;
	
	@Field(name = "nombre_doctor")
	private String nombreDoctor;
	
	@Field(name = "paciente_id")
	private Long pacienteId;
	
	public Notificacion() {
		super();
	}

	public Notificacion(String id, String titulo, String mensaje, LocalDate fecha, String horaCita, LocalDate fechaCita,
			String estadoCita, String nombreDoctor, Long pacienteId) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.mensaje = mensaje;
		this.fecha = fecha;
		this.horaCita = horaCita;
		this.fechaCita = fechaCita;
		this.estadoCita = estadoCita;
		this.nombreDoctor = nombreDoctor;
		this.pacienteId = pacienteId;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalDate getFechaCita() {
		return fechaCita;
	}

	public void setFechaCita(LocalDate fechaCita) {
		this.fechaCita = fechaCita;
	}

	public String getEstadoCita() {
		return estadoCita;
	}

	public void setEstadoCita(String estadoCita) {
		this.estadoCita = estadoCita;
	}

	public String getNombreDoctor() {
		return nombreDoctor;
	}

	public void setNombreDoctor(String nombreDoctor) {
		this.nombreDoctor = nombreDoctor;
	}

	public Long getPacienteId() {
		return pacienteId;
	}

	public void setPacienteId(Long pacienteId) {
		this.pacienteId = pacienteId;
	}

	public String getHoraCita() {
		return horaCita;
	}

	public void setHoraCita(String horaCita) {
		this.horaCita = horaCita;
	}

	@Override
	public String toString() {
		return "Notificacion [id=" + id + ", titulo=" + titulo + ", mensaje=" + mensaje + ", fecha=" + fecha
				+ ", horaCita=" + horaCita + ", fechaCita=" + fechaCita + ", estadoCita=" + estadoCita
				+ ", nombreDoctor=" + nombreDoctor + ", pacienteId=" + pacienteId + "]";
	}
	
	
}
