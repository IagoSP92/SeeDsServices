package com.isp.seeds.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Etiqueta extends AbstractValueObject{

	private Long idEtiqueta = null;
	private String nombreEtiqueta = null;

	public String toString() {
		return super.toString() + ToStringBuilder.reflectionToString(this);
	}

	public Etiqueta () {

	}

	public Long getIdEtiqueta() {
		return idEtiqueta;
	}

	public void setIdEtiqueta(Long idEtiqueta) {
		this.idEtiqueta = idEtiqueta;
	}

	public String getNombreEtiqueta() {
		return nombreEtiqueta;
	}

	public void setNombreEtiqueta(String nombreEtiqueta) {
		this.nombreEtiqueta = nombreEtiqueta;
	}
}
