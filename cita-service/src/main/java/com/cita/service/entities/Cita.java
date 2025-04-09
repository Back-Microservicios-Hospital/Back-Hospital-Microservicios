package com.cita.service.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "citas")
public class Cita {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate fecha;
	private String hora;
	private Long pacienteId;
	private Long doctorId;
	
	@OneToOne
	@JoinColumn(name = "estado_id", referencedColumnName = "id")
	private Estado estado;	
	
	public Cita() {
		super();
	}

	public Cita(Long id, LocalDate fecha, String hora, Long pacienteId, Long doctorId, Estado estado) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.hora = hora;
		this.pacienteId = pacienteId;
		this.doctorId = doctorId;
		this.estado = estado;
	}


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

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	@Override
	public String toString() {
		return "Cita [id=" + id + 
				", fecha=" + fecha + 
				", hora=" + hora + 
				", pacienteId=" + pacienteId + 
				", doctorId="+ doctorId + 
				", estado=" + estado + "]";
	}

	
	
	
}
