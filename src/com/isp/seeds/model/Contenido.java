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
	private Double valoracion = null;
	private Integer reproducciones = null;
	
	private Boolean siguiendo = null;
	private Boolean denunciado = null;
	private Boolean guardado = null;
	private String comentado = null;
	private Double valorado = null;
	
	@Override
	public boolean equals (Object o) {
		
		if (o==null || this==null || !(o instanceof Contenido)) {
			return false;
		}
		Contenido c = (Contenido) o;
		if (id !=null && c.getId()!=null && id==c.getId()) {
			return true;
		}
		return false;
	}

	
	public String toString() {
		return super.toString() + ToStringBuilder.reflectionToString(this);
	}

	public Contenido() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long idContenido) {
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

	public Long getAutor() {
		return autor;
	}

	public void setAutor(Long idAutor) {
		this.autor = idAutor;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Double getValoracion() {
		return valoracion;
	}

	public void setValoracion(Double valoracion) {
		this.valoracion = valoracion;
	}

	public Integer getReproducciones() {
		return reproducciones;
	}

	public void setReproducciones(Integer reproducciones) {
		this.reproducciones = reproducciones;
	}

	public Boolean getSiguiendo() {
		return siguiendo;
	}

	public void setSiguiendo(Boolean siguiendo) {
		this.siguiendo = siguiendo;
	}

	public Boolean getDenunciado() {
		return denunciado;
	}

	public void setDenunciado(Boolean denunciado) {
		this.denunciado = denunciado;
	}

	public Boolean getGuardado() {
		return guardado;
	}

	public void setGuardado(Boolean guardado) {
		this.guardado = guardado;
	}

	public String getComentado() {
		return comentado;
	}

	public void setComentado(String comentario) {
		this.comentado = comentario;
	}

	public Double getValorado() {
		return valorado;
	}

	public void setValorado(Double valorado) {
		this.valorado = valorado;
	}


}
