package com.isp.seeds.dao.spi;

import java.sql.Connection;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Lista;
import com.isp.seeds.service.criteria.ListaCriteria;

public interface ListaDAO {
	
	
	public Lista findById (Connection connection, Long id) 
			throws DataException;

	
	public List <Lista> findByCriteria(Connection connection, ListaCriteria lista) 
			throws DataException;
	

	public List <Lista> findAll(Connection connection) 
			throws DataException;
	

	public Lista create (Connection connection, Lista p) 
			throws DataException;
	
	public void update (Connection connection, Lista p) 
			throws DataException;
	
	public long delete (Connection connection, Long id) 
			throws DataException;
}
