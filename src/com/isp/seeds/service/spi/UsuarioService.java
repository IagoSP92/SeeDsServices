package com.isp.seeds.service.spi;


import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Usuario;

public interface UsuarioService {
	
	public Usuario crearCuenta (Usuario usuario) 
			throws DataException;
	
	public void eliminarCuenta (Long idUsuario) 
			throws DataException;
	
	public Usuario logIn (String email, String contrasena)
			throws DataException;
	
	public void editarPerfil (Usuario usuario)
			throws DataException;
	
	public void cambiarContraseña (String email, String contrasena)
			throws DataException;
	
	public void recuperarContraseña (String email)
			throws DataException;
	
	public Usuario buscarId (Long idUsuario)
			throws DataException;

	public Usuario buscarEmail (String email)
			throws DataException;
	
	public List<Usuario> buscarTodos (int startIndex, int count, String idioma) 
			throws DataException;
	
	
	
}

