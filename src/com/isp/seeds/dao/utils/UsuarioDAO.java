package com.isp.seeds.dao.utils;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import com.isp.seeds.model.Usuario;

public interface UsuarioDAO {
	
	public List <Usuario> findByNombre(Connection connection, String nombre) 
			throws Exception;	
	public List <Usuario> findByNombre(Connection connection, String nombre, Date fechaAlta) 
			throws Exception;
	



	public List <Usuario> findAll(Connection connection) 
			throws Exception;

	public Usuario findById(Connection connection, Long id) 
			throws Exception;
	
	public Usuario create (Connection connection, Usuario u) 
			throws Exception;
	
	public Boolean update (Connection connection, Usuario u) 
			throws Exception;
	
	public void delete (Connection connection, Usuario u) 
			throws Exception;

}
