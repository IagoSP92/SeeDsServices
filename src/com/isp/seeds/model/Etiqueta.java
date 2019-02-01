package com.isp.seeds.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Etiqueta extends AbstractValueObject{

	private Long idEtiqueta = null;
	private String nombreEtiqueta = null;

	/*public String toString() {
		return super.toString() + ToStringBuilder.reflectionToString(this);
	}*/
	
	public String toString() {
		StringBuilder cadena = new StringBuilder();
		cadena.append("Etiqueta - ID: ");
		cadena.append(idEtiqueta);
		cadena.append("   Nombre: ");
		cadena.append(nombreEtiqueta);
		return cadena.toString();
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
