package com.isp.seeds.service.spi;


import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Usuario;

public interface UsuarioService {
	
	public Usuario crearCuenta (Usuario u) 
			throws DataException;
	
	public Usuario eliminarCuenta (Usuario u) 
			throws DataException;
	
	public Usuario logIn (String email, String contrasena, String idioma)
			throws DataException;
	
	public Boolean logOut (String email, String contrasena) // COMO SE FAI ESTO?
			throws DataException;
	
	public void editarPerfil (Usuario usuario, String idioma)
			throws DataException;
	
	public void cambiarContraseņa (String email, String contrasena, String idioma)
			throws DataException;
	
	public void recuperarContraseņa (String email)
			throws DataException;
	

	public Usuario buscarEmail (String email, String idioma)
			throws DataException;
	
	public List<Usuario> buscarTodos (String idioma)
			throws DataException;
	
	
	
}

