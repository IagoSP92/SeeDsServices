package com.isp.seeds.model;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Usuario extends Contenido implements Comparable<Usuario> {

	private String email = null;
	private String contrasena = null;
	private String descripcion = null;
	private String avatarUrl = null;
	private String nombreReal = null;
	private String apellidos = null;
	private String idPais = null;
	private Date fechaNac = null;

	private List<Video> videosSubidos= null;
	private List<Lista> listasSubidas= null;

	private List<Usuario> usuariosSeguidos= null;
	private List<Lista> listasSeguidas= null;

	private List<Video> videosGuardados = null;
	private List<Lista> listasGuardadas = null;


	public String toString() {
		return super.toString() +ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int compareTo(Usuario u) {
		return this.getEmail().compareTo( u.getEmail() );
	}

	public Usuario () {
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getNombreReal() {
		return nombreReal;
	}

	public void setNombreReal(String nombreReal) {
		this.nombreReal = nombreReal;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getPais() {
		return idPais;
	}

	public void setPais(String pais) {
		this.idPais = pais;
	}

	public Date getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public List<Video> getVideosSubidos() {
		return videosSubidos;
	}

	public void setVideosSubidos(List<Video> videosSubidos) {
		this.videosSubidos = videosSubidos;
	}

	public List<Lista> getListasSubidas() {
		return listasSubidas;
	}

	public void setListasSubidas(List<Lista> listasSubidas) {
		this.listasSubidas = listasSubidas;
	}

	public List<Usuario> getUsuariosSeguidos() {
		return usuariosSeguidos;
	}

	public void setUsuariosSeguidos(List<Usuario> usuariosSeguidos) {
		this.usuariosSeguidos = usuariosSeguidos;
	}

	public List<Lista> getListasSeguidas() {
		return listasSeguidas;
	}

	public void setListasSeguidas(List<Lista> listasSeguidas) {
		this.listasSeguidas = listasSeguidas;
	}

	public List<Video> getVideosGuardados() {
		return videosGuardados;
	}

	public void setVideosGuardados(List<Video> videosGuardados) {
		this.videosGuardados = videosGuardados;
	}

	public List<Lista> getListasGuardadas() {
		return listasGuardadas;
	}

	public void setListasGuardadas(List<Lista> listasGuardadas) {
		this.listasGuardadas = listasGuardadas;
	}

}

