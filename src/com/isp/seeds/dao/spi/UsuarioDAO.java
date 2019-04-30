package com.isp.seeds.dao.spi;

import java.sql.Connection;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.model.Usuario;
import com.isp.seeds.service.util.Results;

public interface UsuarioDAO {	
	
	public Usuario create (Connection connection, Usuario usuario) 
			throws DataException;
	
	public void update (Connection connection, Usuario usuario) 
			throws DataException;
	
	public void delete (Connection connection, Long idUsuario) 
			throws DataException;
	
	
	public Usuario findById(Connection connection, Long idSesion, Long idUsuario) 
			throws DataException;
	
	public Usuario findByEmail(Connection connection, String email) 
			throws DataException;

	
	public Boolean verificarContrasena(Connection connection, String email, String contrasena)
			throws DataException;
	
	
	public Results<Contenido> cargarSeguidos(Connection connection, Long idSesion, int startIndex,
			int count) throws DataException;
	

}
