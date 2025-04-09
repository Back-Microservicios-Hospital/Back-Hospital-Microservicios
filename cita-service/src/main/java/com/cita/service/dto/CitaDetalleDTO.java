package com.cita.service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.cita.service.entities.Estado;

public class CitaDetalleDTO {

	private Long id;
	private LocalDate fecha;
	private String hora;
	private PacienteDTO paciente;
	private DoctorDTO doctor;
	private Estado estado;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public PacienteDTO getPaciente() {
		return paciente;
	}
	public void setPaciente(PacienteDTO paciente) {
		this.paciente = paciente;
	}
	public DoctorDTO getDoctor() {
		return doctor;
	}
	public void setDoctor(DoctorDTO doctor) {
		this.doctor = doctor;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "CitaDetalleDTO [id=" + id + 
				", fecha=" + fecha + 
				", paciente=" + paciente 
				+ ", doctor=" + doctor
				+ ", estado=" + estado + "]";
	}
	
	
	
	
}
