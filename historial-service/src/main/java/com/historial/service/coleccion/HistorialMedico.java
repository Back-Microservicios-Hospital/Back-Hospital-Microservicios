package com.historial.service.coleccion;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "historial_medico")
public class HistorialMedico {

	@Id
	private String id;
	
	//Tanto Diagnostico y Receta son embebidos
	private List<Diagnostico> diagnostico;
	private List<Receta> receta;
	private Long pacienteId;
	private Long doctorId;
	
	public HistorialMedico() {
		super();
	}
	public HistorialMedico(String id, List<Diagnostico> diagnostico, List<Receta> receta, Long pacienteId,
			Long doctorId) {
		super();
		this.id = id;
		this.diagnostico = diagnostico;
		this.receta = receta;
		this.pacienteId = pacienteId;
		this.doctorId = doctorId;
	}
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
	
	@Override
	public String toString() {
		return "HistorialMedico [id=" + id + 
				", diagnostico=" + diagnostico + 
				", receta=" + receta + 
				", pacienteId="+ pacienteId + 
				", doctorId=" + doctorId + "]";
	}
	
	

	
	
	
}
