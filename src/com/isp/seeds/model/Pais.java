package com.isp.seeds.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Pais extends AbstractValueObject {
	
	private String idPais;
	private String nombrePais;
	
	
	public String toString() {
		return super.toString() + ToStringBuilder.reflectionToString(this);
	}
	
	public Pais () {
		
	}

	public String getIdPais() {
		return idPais;
	}


	public void setIdPais(String idContenido) {
		this.idPais = idContenido;
	}


	public String getNombrePais() {
		return nombrePais;
	}


	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}
	
	public Pais getPais() {
		return this;
	}

}
