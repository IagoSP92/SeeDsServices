package com.isp.seeds.dao.utils;

import java.sql.Connection;
import java.util.List;

import com.isp.seeds.model.Contenido;
import com.isp.seeds.service.ContenidoCriteria;

public interface ContenidoDAO {
	
	
	
	public Contenido findById(Long id) 
			throws Exception;


	public List<Contenido> findByCriteria(Connection connection, ContenidoCriteria contenido)
			throws Exception;
	
	
	public List<Contenido> findAll(Connection connection) 
			throws Exception;
	
	
	
	public Contenido create (Connection connection, Contenido c)
			throws Exception;
	
	public Contenido update (Connection connection, Contenido c)
			throws Exception;
	
	
	public long delete (Connection connection, Long id) 
			throws Exception;
	
}
