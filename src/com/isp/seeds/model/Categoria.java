package com.isp.seeds.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Categoria extends AbstractValueObject{

	private Long idCategoria=null;
	private String nombreCategoria=null;

	public String toString() {
		return super.toString() + ToStringBuilder.reflectionToString(this);
	}

	public Categoria() {
	}

	public Long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}
	
	public Categoria getCategoria() {
		return this;
	}
}