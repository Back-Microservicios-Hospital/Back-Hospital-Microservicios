package com.cita.service.dto;

import java.time.LocalDate;

public class CitaNuevaNotificacionRequest {

	private LocalDate fechaCita;
	private String hora;
	private Long pacienteId;
	private String doctorAsignado;
	private String estadoCita;
	
	public LocalDate getFechaCita() {
		return fechaCita;
	}
	public void setFechaCita(LocalDate fechaCita) {
		this.fechaCita = fechaCita;
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
	public String getDoctorAsignado() {
		return doctorAsignado;
	}
	public void setDoctorAsignado(String doctorAsignado) {
		this.doctorAsignado = doctorAsignado;
	}
	public String getEstadoCita() {
		return estadoCita;
	}
	public void setEstadoCita(String estadoCita) {
		this.estadoCita = estadoCita;
	}
	
	@Override
	public String toString() {
		return "CitaNuevaNotificacionRequest [fecha_cita=" + fechaCita + ", hora=" + hora + ", pacienteId=" + pacienteId
				+ ", doctorAsignado=" + doctorAsignado + ", estadoCita=" + estadoCita + "]";
	}
	
	
	
}
