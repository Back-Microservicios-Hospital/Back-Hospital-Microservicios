package com.cita.service.dto;

import java.time.LocalDate;

public class CitaDTO {

	private LocalDate fecha;
	private String hora;
	private Long pacienteId;
	private Long doctorId;
	private Long estadoId;
	
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
	public Long getPacienteId() {
		return pacienteId;
	}
	public void setPacienteId(Long pacienteId) {
		this.pacienteId = pacienteId;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public Long getEstadoId() {
		return estadoId;
	}
	public void setEstadoId(Long estadoId) {
		this.estadoId = estadoId;
	}
	@Override
	public String toString() {
		return "CitaDTO [fecha=" + fecha + ", hora=" + hora + ", pacienteId=" + pacienteId + ", doctorId=" + doctorId
				+ ", estadoId=" + estadoId + "]";
	}
	
	
	
}
