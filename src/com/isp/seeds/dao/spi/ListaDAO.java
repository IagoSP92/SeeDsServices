package com.isp.seeds.dao.spi;

import java.sql.Connection;
import java.util.List;

import com.isp.seeds.model.Lista;
import com.isp.seeds.service.ListaCriteria;

public interface ListaDAO {
	
	
	public String findById (Connection connection, Long id) 
			throws Exception;

	
	public List <Lista> findByCriteria(Connection connection, ListaCriteria lista) 
			throws Exception;
	

	public List <Lista> findAll(Connection connection) 
			throws Exception;
	

	public Lista create (Connection connection, Lista p) 
			throws Exception;
	
	public Boolean update (Connection connection, Lista p) 
			throws Exception;
	
	public void delete (Connection connection, Long id) 
			throws Exception;
}
