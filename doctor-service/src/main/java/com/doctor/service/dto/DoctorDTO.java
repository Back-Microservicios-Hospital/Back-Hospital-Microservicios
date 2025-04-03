package com.doctor.service.dto;

public class DoctorDTO {

	private String nombre;
	private String apellido;
	private String telefono;
	private String email;
	
	private Long especialidad_id;

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

	public Long getEspecialidad_id() {
		return especialidad_id;
	}

	public void setEspecialidad_id(Long especialidad_id) {
		this.especialidad_id = especialidad_id;
	}
	
	
}
