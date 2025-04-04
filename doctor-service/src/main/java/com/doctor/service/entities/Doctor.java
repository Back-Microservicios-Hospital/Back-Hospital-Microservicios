package com.doctor.service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctores")
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String apellido;
	private String telefono;
	private String email;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "especialidad_id")
	private Especialidad especialidad;
	
	public Doctor() {
		super();
	}
	
	public Doctor(Long id, String nombre, String apellido, String telefono, String email, Especialidad especialidad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.email = email;
		this.especialidad = especialidad;
	}




	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public Especialidad getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	@Override
	public String toString() {
		return "Doctor [id=" + id + 
				", nombre=" + nombre + 
				", apellido=" + apellido + 
				", telefono=" + telefono
				+ ", email=" + email + 
				", especialidad=" + especialidad + "]";
	}
	
	
	
	
}
