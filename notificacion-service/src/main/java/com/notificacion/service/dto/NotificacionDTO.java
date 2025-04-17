package com.notificacion.service.dto;

import java.time.LocalDate;

public class NotificacionDTO {

	private String titulo;
	private String mensaje;
	private LocalDate fecha;
	private LocalDate fechaCita;
	private String estadoCita;
	private String nombreDoctor;
	private Long pacienteId;
	
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
	
	
}
