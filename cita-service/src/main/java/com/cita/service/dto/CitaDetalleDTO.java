package com.cita.service.dto;

import java.util.Date;

import com.cita.service.entities.Estado;

public class CitaDetalleDTO {

	private Long id;
	private Date fecha;
	private PacienteDTO paciente;
	private DoctorDTO doctor;
	private Estado estado;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
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
