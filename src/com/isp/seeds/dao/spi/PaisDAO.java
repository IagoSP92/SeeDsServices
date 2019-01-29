package com.isp.seeds.dao.spi;

import java.sql.Connection;
import java.util.List;

import com.isp.seeds.Exceptions.DataException;
import com.isp.seeds.model.Pais;

public interface PaisDAO {

	public Pais findById(Connection c, String id) 
			throws DataException;


	public List<Pais> findAll(Connection c) 
			throws DataException;
}
