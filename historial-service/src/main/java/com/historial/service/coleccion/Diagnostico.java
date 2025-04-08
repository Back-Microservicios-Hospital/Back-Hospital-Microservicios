package com.historial.service.coleccion;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "diagnostico")
public class Diagnostico {

	private String descripcion;
	private LocalDate fecha;
	
	public Diagnostico() {
		super();
	}
	public Diagnostico(String descripcion, LocalDate fecha) {
		super();
		this.descripcion = descripcion;
		this.fecha = fecha;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	
}
