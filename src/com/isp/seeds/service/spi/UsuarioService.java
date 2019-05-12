package com.isp.seeds.service.spi;


import com.isp.seeds.exceptions.DataException;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Usuario;
import com.isp.seeds.service.util.Results;

public interface UsuarioService {
	
	public Usuario crearCuenta (Usuario usuario) 
			throws DataException;
	
	public void eliminarCuenta (Long idUsuario) 
			throws DataException;
	
	public Usuario logIn (String email, String contrasena)
			throws DataException;
	
	public void editarPerfil (Usuario usuario)
			throws DataException;
	
	public void cambiarContrase�a (String email, String contrasena)
			throws DataException;
	
	
	public Usuario buscarId (Long idSesion, Long idUsuario)
			throws DataException;

	public Usuario buscarEmail (String email)
			throws DataException;
	
	public Results<Contenido> cargarSeguidos(Long idSesion, int startIndex,
			int count) throws DataException;

	
	
}

