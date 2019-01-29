package com.isp.seeds.dao.spi;

import java.sql.Connection;
import java.util.List;

import com.isp.seeds.model.Usuario;
import com.isp.seeds.service.criteria.UsuarioCriteria;

public interface UsuarioDAO {
	
	
	public Usuario findById(Connection connection, Long id) 
			throws Exception;
	
	public List <Usuario> findByCriteria(Connection connection, UsuarioCriteria usuario) 
			throws Exception;
	

	public List <Usuario> findAll(Connection connection) 
			throws Exception;


	
	public Usuario create (Connection connection, Usuario u) 
			throws Exception;
	
	public void update (Connection connection, Usuario u) 
			throws Exception;
	
	public long delete (Connection connection, Long id) 
			throws Exception;

}
