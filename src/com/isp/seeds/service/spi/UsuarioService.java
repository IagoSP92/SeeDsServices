package com.isp.seeds.service.spi;


import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Usuario;

public interface UsuarioService {
	
	public Usuario crearCuenta (Usuario u) 
			throws DataException;
	
	public Usuario eliminarCuenta (Usuario u) 
			throws DataException;
	
	public Usuario login (String email, String contrasena)
			throws DataException;
	
	public Usuario editarPerfil (Usuario usuario)
			throws DataException;
	
	public void cambiarContraseņa (String email, String contrasena)
			throws DataException;
	
	public void recuperarContraseņa (String email)
			throws DataException;
}
