package com.historial.service.dto;

import java.util.List;

import com.historial.service.coleccion.Diagnostico;
import com.historial.service.coleccion.Receta;

public class HistorialMedicoDTO {

	private List<Diagnostico> diagnostico;
	private List<Receta> receta;
	private Long paciendId;
	private Long doctorId;
	
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
	public Long getPaciendId() {
		return paciendId;
	}
	public void setPaciendId(Long paciendId) {
		this.paciendId = paciendId;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	
	
}
