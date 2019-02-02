package com.isp.seeds.service.criteria;

import java.util.Date;

import com.isp.seeds.model.Contenido;

public class ContenidoCriteria extends Contenido {
	
	private Date fechaAltaHasta = null;
	private Date fechaModHasta = null;
	
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


}
