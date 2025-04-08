package com.historial.service.dto;

import java.util.List;

import com.historial.service.coleccion.Diagnostico;
import com.historial.service.coleccion.Receta;

public class HistorialMedicoDetalleDTO {

	private String id;
	private List<Diagnostico> dianostico;
	private List<Receta> receta;
	private DoctorDTO doctor;
	private PacienteDTO paciente;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Diagnostico> getDianostico() {
		return dianostico;
	}
	public void setDianostico(List<Diagnostico> dianostico) {
		this.dianostico = dianostico;
	}
	public List<Receta> getReceta() {
		return receta;
	}
	public void setReceta(List<Receta> receta) {
		this.receta = receta;
	}
	public DoctorDTO getDoctor() {
		return doctor;
	}
	public void setDoctor(DoctorDTO doctor) {
		this.doctor = doctor;
	}
	public PacienteDTO getPaciente() {
		return paciente;
	}
	public void setPaciente(PacienteDTO paciente) {
		this.paciente = paciente;
	}
	
	
}
