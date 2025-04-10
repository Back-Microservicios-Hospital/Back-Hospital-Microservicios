package com.historial.service.dto;

import java.util.List;

import com.historial.service.coleccion.Diagnostico;
import com.historial.service.coleccion.Receta;

public class HistorialMedicoDetalleDTO {

	private String id;
	private List<Diagnostico> diagnostico;
	private List<Receta> receta;
	private DoctorDTO doctor;
	private PacienteDTO paciente;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public List<Diagnostico> getDiagnostico() {
		return diagnostico;
	}
	public void setDiagnostico(List<Diagnostico> diagnostico) {
		this.diagnostico = diagnostico;
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
	@Override
	public String toString() {
		return "HistorialMedicoDetalleDTO [id=" + id + ", diagnostico=" + diagnostico + ", receta=" + receta
				+ ", doctor=" + doctor + ", paciente=" + paciente + "]";
	}
	
	
}
