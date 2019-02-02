package com.isp.seeds.service.spi;


import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Usuario;

public interface UsuarioService {
	
	public Usuario crearCuenta (Usuario u) 
			throws DataException;
	
	public Usuario eliminarCuenta (Usuario u) 
			throws DataException;
	
	public Usuario logIn (String email, String contrasena)
			throws DataException;
	
	public Boolean logOut (String email, String contrasena)
			throws DataException;
	
	public Usuario editarPerfil (Usuario usuario)
			throws DataException;
	
	public void cambiarContrase�a (String email, String contrasena)
			throws DataException;
	
	public void recuperarContrase�a (String email)
			throws DataException;
}
