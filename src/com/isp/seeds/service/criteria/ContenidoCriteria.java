package com.isp.seeds.service.criteria;

import java.util.Date;

import com.isp.seeds.model.Contenido;

public class ContenidoCriteria extends Contenido {
	
	private Boolean aceptarUsuario = null;
	private Boolean aceptarVideo = null;
	private Boolean aceptarLista = null;
	
	
	// Se utiliza como "Fecha Desde" la fecha de la clase contenido:
	private Date fechaAltaHasta = null;
	private Date fechaModHasta = null;
	
	private Double valoracionMin = null;
	private Double valoracionMax = null;
	
	private Integer reproduccionesMin = null;
	private Integer reproduccionesMax = null;
	
	private Long categoria = null;

	
	public ContenidoCriteria () {
		
	}

	public Boolean getAceptarUsuario() {
		return aceptarUsuario;
	}

	public void setAceptarUsuario(Boolean aceptarUsuario) {
		this.aceptarUsuario = aceptarUsuario;
	}

	public Boolean getAceptarVideo() {
		return aceptarVideo;
	}

	public void setAceptarVideo(Boolean aceptarVideo) {
		this.aceptarVideo = aceptarVideo;
	}

	public Boolean getAceptarLista() {
		return aceptarLista;
	}

	public void setAceptarLista(Boolean aceptarLista) {
		this.aceptarLista = aceptarLista;
	}

	public Date getFechaAltaHasta() {
		return fechaAltaHasta;
	}

	public void setFechaAltaHasta(Date fechaAltaHasta) {
		this.fechaAltaHasta = fechaAltaHasta;
	}

	public Date getFechaModHasta() {
		return fechaModHasta;
	}

	public void setFechaModHasta(Date fechaModHasta) {
		this.fechaModHasta = fechaModHasta;
	}
	

	public Double getValoracionMin() {
		return valoracionMin;
	}

	public void setValoracionMin(Double valoracion) {
		this.valoracionMin = valoracion;
	}
	
	public Double getValoracionMax() {
		return valoracionMax;
	}

	public void setValoracionMax(Double valoracion) {
		this.valoracionMax = valoracion;
	}

	
	public Integer getReproduccionesMin() {
		return reproduccionesMin;
	}

	public void setReproduccionesMin(Integer reproducciones) {
		this.reproduccionesMin = reproducciones;
	}
	
	public Integer getReproduccionesMax() {
		return reproduccionesMax;
	}

	public void setReproduccionesMax(Integer reproducciones) {
		this.reproduccionesMax = reproducciones;
	}

	public Long getCategoria() {
		return categoria;
	}

	public void setCategoria(Long categoria) {
		this.categoria = categoria;
	}



}
