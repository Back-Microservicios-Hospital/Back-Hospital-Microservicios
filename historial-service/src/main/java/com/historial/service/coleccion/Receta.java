package com.historial.service.coleccion;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "receta")
public class Receta {

	private String medicamento;
	private String dosis;
	private int cantidad;
	
	public Receta() {
		super();
	}
	public Receta(String medicamento, String dosis, int cantidad) {
		super();
		this.medicamento = medicamento;
		this.dosis = dosis;
		this.cantidad = cantidad;
	}
	public String getMedicamento() {
		return medicamento;
	}
	public void setMedicamento(String medicamento) {
		this.medicamento = medicamento;
	}
	public String getDosis() {
		return dosis;
	}
	public void setDosis(String dosis) {
		this.dosis = dosis;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
}
