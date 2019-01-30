package com.isp.seeds.dao.spi;

import java.sql.Connection;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Contenido;
import com.isp.seeds.service.criteria.ContenidoCriteria;

public interface ContenidoDAO {
	
	
	
	public Contenido findById(Connection connection, Contenido contenido, Long id) 
			throws DataException;


	public List<Contenido> findByCriteria(Connection connection, ContenidoCriteria contenido)
			throws DataException;
	
	
	public List<Contenido> findAll(Connection connection) 
			throws DataException;
	
	
	
	public Contenido create (Connection connection, Contenido c)
			throws DataException;
	
	public void update (Connection connection, Contenido c)
			throws DataException;
	
	
	public long delete (Connection connection, Long id) 
			throws DataException;
	
}
