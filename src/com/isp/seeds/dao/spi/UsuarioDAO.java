package com.isp.seeds.dao.spi;

import java.sql.Connection;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Usuario;
import com.isp.seeds.service.criteria.UsuarioCriteria;

public interface UsuarioDAO {
	
	
	public Usuario findById(Connection connection, Long idUsuario) 
			throws DataException;
	
	public Usuario findByEmail(Connection connection, String email) 
			throws DataException;
	
//	public List <Usuario> findByCriteria(Connection connection, UsuarioCriteria usuario) 
//			throws DataException;
	

	public List <Usuario> findAllUsers(Connection connection) 
			throws DataException;


	
	public Usuario create (Connection connection, Usuario usuario) 
			throws DataException;
	
	public void update (Connection connection, Usuario usuario) 
			throws DataException;
	
	public void delete (Connection connection, Long idUsuario) 
			throws DataException;
	
	public Boolean verificarContrasena(Connection connection, String email, String contrasena)
			throws DataException;
	

}
