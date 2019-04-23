package com.isp.seeds.model;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Video extends Contenido  implements Comparable<Video> {

	private String descripcion = null;
	private String url = null;

	private Categoria categoria= null;
	private List<Etiqueta> etiquetas = null;
	private List<String> comentarios= null;

	public String toString() {
		return super.toString() + ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int compareTo(Video v) {
		return this.getValoracion().compareTo( v.getValoracion() );
	}

	public Video () {
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	public List<Etiqueta> getEtiqueta() {
		return etiquetas;
	}


	public void setEtiqueta(List<Etiqueta> etiquetas) {
		this.etiquetas = etiquetas;
	}


	public List<String> getComentarios() {
		return comentarios;
	}


	public void setComentarios(List<String> comentarios) {
		this.comentarios = comentarios;
	}
}
