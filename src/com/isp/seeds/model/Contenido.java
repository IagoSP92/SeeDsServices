package com.isp.seeds.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Contenido extends AbstractValueObject {

	private Long id = null;
	private String nombre = null;
	private Date fechaAlta = null;
	private Date fechaMod = null;
	private Long autor = null;
	private Integer tipo = null;

	
	/*public String toString() {
		return super.toString() + ToStringBuilder.reflectionToString(this);
	}*/
	
	public String toString() {
		StringBuilder cadena = new StringBuilder();
		cadena.append("Contenido - ID: ");
		cadena.append(id);
		cadena.append("   Nombre: ");
		cadena.append(nombre);
		cadena.append("   F.Alta: ");
		cadena.append(fechaAlta);
		cadena.append("   F.Mod: ");
		cadena.append(fechaMod);
		cadena.append("   Autor: ");
		cadena.append(autor);
		cadena.append("   Tipo: ");
		cadena.append(tipo);
		return cadena.toString();
	}

	public Contenido() {

	}

	public Long getIdContenido() {
		return id;
	}

	public void setIdContenido(Long idContenido) {
		this.id = idContenido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaMod() {
		return fechaMod;
	}

	public void setFechaMod(Date fechaMod) {
		this.fechaMod = fechaMod;
	}

	public Long getIdAutor() {
		return autor;
	}

	public void setIdAutor(Long idAutor) {
		this.autor = idAutor;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}


}
