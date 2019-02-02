package com.isp.seeds.service.criteria;

import java.util.Date;

import com.isp.seeds.model.Contenido;

public class ContenidoCriteria extends Contenido {
	
	private Date fechaAltaHasta = null;
	private Date fechaModHasta = null;
	
	private Integer valoracionMin = null;
	private Integer valoracionMax = null;
	
	private Integer reproduccionesMin = null;
	private Integer reproduccionesMax = null;

	
	public ContenidoCriteria () {
		
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
	

	public Integer getValoracionMin() {
		return valoracionMin;
	}

	public void setValoracionMin(Integer valoracion) {
		this.valoracionMin = valoracion;
	}
	
	public Integer getValoracionMax() {
		return valoracionMax;
	}

	public void setValoracionMax(Integer valoracion) {
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



}
