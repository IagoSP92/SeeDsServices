package com.isp.seeds.model;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Lista extends Contenido {

	private String descripcion = null;
	private Boolean publica = null;
	private Categoria categoria= null;

	private List<Comentario> comentarios= null;
	private List<Video> videos= null;

	public String toString() {
		return super.toString() + ToStringBuilder.reflectionToString(this);
	}

	public Lista() {
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getPublica() {
		return publica;
	}

	public void setPublica(Boolean publica) {
		this.publica = publica;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}
}
